/**
 * Goal model class
 * @class Goal
 * @property {Number} id
 * @property {String} image
 * @property {String} title
 * @author Justin Chan
 */

export class Goal {
  id
  userId
  image
  title
  sdgId

  constructor (id, userId, title, sdgId) {
    this.id = id
    this.userId = userId
    this.image = this.makeImage(parseInt(sdgId))
    this.title = title
    this.sdgId = sdgId
  }

  /**
   * Function assigns the icon to its goal
   * @return {*}
   * @author Justin Chan
   * @param sdgId
   */
  makeImage (sdgId) {
    switch (parseInt(sdgId)) {
      case 1:
        return 'E-WEB-Goal-01.png'
      case 2:
        return 'E-WEB-Goal-02.png'
      case 3:
        return 'E-WEB-Goal-03.png'
      case 4:
        return 'E-WEB-Goal-04.png'
      case 5:
        return 'E-WEB-Goal-05.png'
      case 6:
        return 'E-WEB-Goal-06.png'
      case 7:
        return 'E-WEB-Goal-07.png'
      case 8:
        return 'E-WEB-Goal-08.png'
      case 9:
        return 'E-WEB-Goal-09.png'
      case 10:
        return 'E-WEB-Goal-10.png'
      case 11:
        return 'E-WEB-Goal-11.png'
      case 12:
        return 'E-WEB-Goal-12.png'
      case 13:
        return 'E-WEB-Goal-13.png'
      case 14:
        return 'E-WEB-Goal-14.png'
      case 15:
        return 'E-WEB-Goal-15.png'
      case 16:
        return 'E-WEB-Goal-16.png'
      case 17:
        return 'E-WEB-Goal-17.png'
      default:
        console.error('Image cannot be found for goal: ' + sdgId)
        return require('@/assets/img/logos/logo.png')
    }
  }

  static copyConstructor (goal) {
    if (goal === null || goal === undefined) return null
    console.log(goal.sdgId)
    return Object.assign(new Goal(0), goal)
  }
}
