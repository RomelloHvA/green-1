import Question from './Question'

/**
 * QuizQuestionTrueFalse model class.
 * @class QuizQuestionTrueFalse
 * @extends Question
 * @property {Boolean} givenAnswer
 * @author Marco de Boer
 */

export default class YesNoQuestion extends Question {
  givenAnswer
  type = 'yesno'
  sdg
  sdgIsEmpty

  /**
   * This class is for questions that only have 2 options as answers
   * @param {String} question
   * @param {String} imgPath
   * @param {Number} SDG
   */
  constructor (id = null, index, question = null, imgPath = null, sdg = null) {
    super(id, index, question, imgPath)
    this.givenAnswer = null
    this.sdg = sdg
  }

  /**
   * This functions sets the givenAnswer
   * @param {boolean} answer
   * @author Marco de Boer
   */

  setGivenAnswer (answer) {
    if (answer === undefined) throw new Error('answer is undefined')
    if (answer !== Boolean(answer)) throw new Error('answer is not a boolean')

    this.givenAnswer = answer
  }

  async clone () {
    const clone = new YesNoQuestion(this.id, this.index, this.question, this.imgPath, this.sdg)
    clone.givenAnswer = this.givenAnswer
    return clone
  }

  equals (other) {
    if (other === null || other === undefined) return false
    if (super.equals(other) === false) return false
    if (this.sdg !== other.sdg) return false
    return true
  }

  static copyConstructor (questionFromJson) {
    if (questionFromJson !== null && questionFromJson && questionFromJson !== undefined && typeof questionFromJson === 'object') {
      return new YesNoQuestion(questionFromJson.id, questionFromJson.index, questionFromJson.question, questionFromJson.imgPath, questionFromJson.sdg)
    }
    return null
  }
}
