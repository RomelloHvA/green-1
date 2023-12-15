import QuizQuestionTrueFalse from '@/models/YesNoQuestion'
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import QuizResult from '@/models/QuizResult'
import Sector from './Sector'

/**
 * Quiz model class.
 * @class Quiz
 * @property {String} name
 * @property {Array} questionObjectArray array of questions
 * @property {Number} currentQuestionIndex
 * @property {Number} totalQuestions
 * @property {Number} totalAnsweredQuestions
 * @property {Array} quizResultObjectArray array of quizResults (SDG, score)
 * @author Marco de Boer
 */

export default class Quiz {
  id
  name
  nameIsEmpty
  quizQuestions
  currentQuestionIndex
  totalQuestions
  totalAnsweredQuestions
  quizResultObjectArray
  isConcept
  isPublished
  isLive
  sector

  /**
   * For the constructor is only json needed the json needs to be in the following format:
   * {
   * "name": "name of the quiz",
   * "questions": []
   * }
   * @param {JSON} questionJSON
   * @author Marco de Boer
   */

  constructor (questionJSON, isInQuizBuilder = false) {
    if (questionJSON === undefined) throw new Error('JSON is undefined')

    if (questionJSON.id === 0) {
      this.id = 0
      this.isConcept = true
      this.isPublished = false
      this.totalQuestions = 0
      this.sector = null
      return
    }

    if (questionJSON.name === undefined) throw new Error('name is undefined')

    if (questionJSON.quizQuestions === undefined) throw new Error('questions is undefined')

    this.id = questionJSON.id ? questionJSON.id : null
    this.name = questionJSON.name
    this.quizQuestions = []
    this.totalQuestions = 0
    if (questionJSON.quizQuestions !== null && questionJSON.quizQuestions.length !== 0) {
      this.#instatiateQuiz(questionJSON.quizQuestions).then(() => {
        this.totalQuestions = this.quizQuestions.length
      })
    }

    this.isConcept = questionJSON.isConcept
    this.isPublished = questionJSON.isPublished
    this.isLive = questionJSON.isLive
    this.sector = questionJSON.sector ? new Sector(questionJSON.sector) : null

    if (!isInQuizBuilder) {
      if (questionJSON.quizQuestions !== null && questionJSON.quizQuestions.length !== 0) {
        this.#instantieQuizResults()
      }
      this.currentQuestionIndex = 0
      this.totalAnsweredQuestions = 0
    }
  }

  /**
   * This functions is used to make from each question a Question object
   * The JSON only contains the questions from the original JSON send to the contructor
   * @param {JSON} questionJSON
   * @author Marco de Boer
   */
  async #instatiateQuiz (questionJSON) {
    try {
      if (questionJSON === undefined || questionJSON.length === 0) throw new Error('JSON is undefined or length is 0')
      for (const question of questionJSON) {
        if (question.type === 'multiplechoice') {
          this.quizQuestions.push(new MultipleChoiceQuestion(question.id, question.index, question.question, question.imgPath, question.options, question.answerLimit))
        } else if (question.type === 'yesno') {
          this.quizQuestions.push(new QuizQuestionTrueFalse(question.id, question.index, question.question, question.imgPath, question.sdg))
        } else throw new Error('Question type is not valid')
      }
    } catch (error) {
      console.log(error)
    }
  }

  /**
   * This functions is used to make from each SDG a QuizResult object
   * The score is set to 0 by default
   * @private
   * @author Marco de Boer
   */
  async #instantieQuizResults () {
    this.quizResultObjectArray = []
    for (let i = 1; i < 18; i++) {
      this.quizResultObjectArray.push(new QuizResult(i, 0))
    }
  }

  async getCurrentQuestion () {
    return this.quizQuestions[this.currentQuestionIndex]
  }

  async getNextQuestion () {
    if (this.currentQuestionIndex >= this.quizQuestions.length) throw new Error('Cant get Next Question, there are no more questions')

    this.currentQuestionIndex++
    return this.quizQuestions[this.currentQuestionIndex]
  }

  async getPreviousQuestion () {
    if (this.currentQuestionIndex <= 0) throw new Error('Cant get Previous Question, there are no more questions')

    this.currentQuestionIndex--
    return this.quizQuestions[this.currentQuestionIndex]
  }

  /**
   * This function calculates how many questions have been answered in the quiz this is used for navigation in QuizComponent
   * @returns {Number} totalAnsweredQuestions
   * @author Marco de Boer
   */
  async getTotalAnsweredQuestions () {
    this.totalAnsweredQuestions = 0
    for (const question of this.quizQuestions) {
      if (question instanceof QuizQuestionTrueFalse && question.givenAnswer !== null) {
        this.totalAnsweredQuestions++
      } else if (question instanceof MultipleChoiceQuestion && question.givenAnswers.length !== 0) {
        this.totalAnsweredQuestions++
      }
    }
    return this.totalAnsweredQuestions
  }

  /**
   * This function calculates the score per SDG. It goes trough each question and checks if the givenAnswer is true and
   * sets the score of the SDG of that answer to +1
   * @returns {Array} quizResultObjectArray array of quizResults (SDG, score) this is used for the result page in QuizComponent
   * @author Marco de Boer
   */

  async setQuizResultObjectArray () {
    for (const question of this.quizQuestions) {
      if (question instanceof QuizQuestionTrueFalse) {
        if (question.givenAnswer === true) {
          this.quizResultObjectArray[question.sdg - 1].score += 1
        }
      } else if (question instanceof MultipleChoiceQuestion) {
        for (let i = 0; i < question.givenAnswers.length; i++) {
          if (question.givenAnswers[i] === true) {
            this.quizResultObjectArray[question.options[i].sdg - 1].score += 1
          }
        }
      }
    }
    return this.quizResultObjectArray
  }

  static copyConstructor (quizFromJson) {
    if (quizFromJson !== null && quizFromJson.length !== 0 && quizFromJson !== undefined) {
      return new Quiz(quizFromJson)
    }
    return null
  }

  static copyBuilderConstructor (quizFromJson) {
    if (quizFromJson !== null && quizFromJson.length !== 0 && quizFromJson !== undefined) {
      return new Quiz(quizFromJson, true)
    }
    return null
  }

  async clone () {
    const clone = new Quiz(this)
    return clone
  }

  equals (other) {
    if (other === null || other === undefined) return false
    if (this.name !== other.name) return false
    if (this.isConcept !== other.isConcept) return false
    if (this.isPublished !== other.isPublished) return false
    if (this.quizQuestionsArrayEquals(other.quizQuestions) === false) return false
    if (other.sector !== null) {
      if (other.sector.equals(this.sector) === false) return false
    }
    return true
  }

  quizQuestionsArrayEquals (otherQuizQuestionsObjectArray) {
    if (this.quizQuestions.length !== otherQuizQuestionsObjectArray.length) return false
    for (let i = 0; i < this.quizQuestions.length; i++) {
      if (this.quizQuestions[i].equals(otherQuizQuestionsObjectArray[i]) === false) return false
    }
    return true
  }
}
