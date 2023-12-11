/**
 * QuizResult model
 * @class QuizResult
 * @property {Number} SDG
 * @property {Number} score
 * @author Marco de Boer
 */
export default class QuizResult {
  SDG
  score

  /**
   * This class is used for formatting the results of the quiz
   * @param {Number} SDG
   * @param {Number} score
   */
  constructor (SDG, score = 0) {
    if (SDG === undefined || score === undefined) throw new Error('SDG or score is undefined')
    if (SDG < 1 || SDG > 17) throw new Error('SDG is not between 1 and 17')
    if (score !== Number(score) || SDG !== Number(SDG)) throw new Error('score is not a number')

    this.SDG = SDG
    this.score = score
  }
}
