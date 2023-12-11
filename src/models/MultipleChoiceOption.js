/**
 * MultipleChoiceOption model
 * @class MultipleChoiceOption
 * @property {String} option
 * @property {Boolean} isSelected
 * @author Marco de Boer
 */
export default class MultipleChoiceOption {
  id
  option
  optionIsEmpty
  isSelected
  sdg
  sdgIsEmpty

  /**
   * This is a class for the options of a multiple choice question
   * @param {String} option
   * @param {Boolean} isSelected
   */

  constructor (id = null, option = null, isSelected = false, sdg = null) {
    if (option === undefined) throw new Error('option is undefined')
    if (isSelected !== Boolean(isSelected)) throw new Error('isSelected is not a boolean')
    this.id = id
    this.option = option
    this.isSelected = isSelected
    this.sdg = sdg
  }

  /**
   * This function toggles the isSelected boolean
   */
  async toggleSelected () {
    this.isSelected = !this.isSelected
  }

  equals (other) {
    return this.id === other.id &&
           this.option === other.option &&
           this.sdg === other.sdg
  }
}
