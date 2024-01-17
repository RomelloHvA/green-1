/**
 * Question model class.
 * @class Question
 * @property {String} question
 * @property {Number} SDG can be an array of numbers
 * @author Marco de Boer
 */
export default class Question {
  id
  index
  imgPath
  question
  questionIsEmpty

  /**
   * This constructor is not meant to be used directly, but to be extended by other classes.
   * @param {String} question
   * @param {Number} SDG can be an array of numbers
   */
  constructor (id = null, index, question, imgPath = null) {
    this.id = id
    this.index = index
    this.question = question
    this.imgPath = imgPath
  }

  equals (other) {
    if (this.index !== other.index) return false
    if (this.imgPath !== other.imgPath) return false
    if (this.question !== other.question) return false
    return true
  }
}
