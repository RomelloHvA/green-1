import Question from './Question'
import MultipleChoiceOption from './MultipleChoiceOption'

/**
 * MultipleChoiceQuestion model class.
 * @class MultipleChoiceQuestion
 * @extends Question
 * @property {Array} optionsObjectArray array of options (possible answers) for the question
 * @property {Array} givenAnswers array of booleans
 * @property {Number} answerLimit number of answers that can be given (default is the amount of options)
 * @author Marco de Boer
 */

export default class MultipleChoiceQuestion extends Question {
  options
  givenAnswers
  answerLimit
  answerLimitIsValid = true
  type = 'multiplechoice'

  /**
   * This class is for questions that have more than 2 options as answers
   * @param {String} question
   * @param {Array} SDG array of numbers
   * @param {Array} options array of strings
   * @param {Number} answerLimit number of answers that can be given (default is the amount of options)
   */
  constructor (id = null, index, question = null, imgPath = null, options, answerLimit = 0) {
    super(id, index, question, imgPath)
    this.#initializeOptions(options)
    this.givenAnswers = []
    this.answerLimit = answerLimit
  }

  /**
   * This functions is used to make from each option a MultipleChoiceOption object
   * this object contains the option and a boolean that says if it is selected or not
   * @private
   * @param {Array} options
   * @author Marco de Boer
   */

  #initializeOptions (options) {
    this.options = []
    if (options === null) return
    for (const option of options) {
      if (option === null) throw new Error('option is null')
      this.options.push(new MultipleChoiceOption(option.id, option.option, false, option.sdg))
    }
  }

  /**
   * This functions sets the givenAnswers by checking for each optionObject if isSelected is true
   * The total amount of givenAnswers is equal to the total amount of options so if an option is not selected it is false
   * @author Marco de Boer
   */
  async setGivenAnswers () {
    for (let i = 0; i < this.options.length; i++) {
      if (this.options[i].isSelected) {
        this.givenAnswers[i] = true
      } else {
        this.givenAnswers[i] = false
      }
    }
  }

  /**
   * This function get the amount of selected answers this can be used to know if the answerLimit is reached
   * @returns {Number} amount of selected answers
   * @author Marco de Boer
   */
  async getSelectedAnswersAmount () {
    let counter = 0
    for (const option of this.options) {
      if (option.isSelected) {
        counter++
      }
    }
    return counter
  }

  async clone () {
    const clone = new MultipleChoiceQuestion(this.id, this.index, this.question, this.imgPath, this.options, this.answerLimit)
    clone.givenAnswers = this.givenAnswers
    return clone
  }

  static copyConstructor (questionFromJson) {
    if (questionFromJson !== null && questionFromJson && questionFromJson !== undefined) {
      return new MultipleChoiceQuestion(questionFromJson.id, questionFromJson.index, questionFromJson.question, questionFromJson.imgPath, questionFromJson.options, questionFromJson.answerLimit)
    }
    return null
  }

  equals (other) {
    if (other === null || other === undefined) return false
    if (super.equals(other) === false) return false
    if (this.optionsArrayEquals(other.options) === false) return false
    if (this.answerLimit !== other.answerLimit) return false
    return true
  }

  optionsArrayEquals (otherOptionsObjectArray) {
    if (this.options.length !== otherOptionsObjectArray.length) return false
    for (let i = 0; i < this.options.length; i++) {
      if (this.options[i].equals(otherOptionsObjectArray[i]) === false) return false
    }
    return true
  }
}
