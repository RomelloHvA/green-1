/**
 * Sector model
 * @class Sector
 * @property {Number} id
 * @property {String} name
 * @property {String} description
 * @author Marco de Boer
 */

export default class Sector {
  id
  name
  description
  quizzes = []

  constructor (sectorJSON) {
    if (sectorJSON === undefined) throw new Error('JSON is undefined')

    if (sectorJSON.id === undefined) throw new Error('id is undefined')

    if (sectorJSON.name === undefined) throw new Error('name is undefined')

    if (sectorJSON.description === undefined) throw new Error('description is undefined')

    this.id = sectorJSON.id
    this.name = sectorJSON.name
    this.description = sectorJSON.description
  }

  static copyConstructor (sectorJSON) {
    if (sectorJSON !== null && sectorJSON.length !== 0 && sectorJSON !== undefined) {
      return new Sector(sectorJSON)
    }
    return null
  }

  equals (other) {
    if (!(other instanceof this.constructor)) {
      return false
    }
    return (
      other.id === this.id &&
      other.name === this.name &&
      other.description === this.description
    )
  }

  setQuizzes (quizzes) {
    this.quizzes = []
    for (const quiz of quizzes) {
      if (quiz.sector !== null && quiz.sector.equals(this)) {
        this.quizzes.push(quiz)
      }
    }
  }
}
