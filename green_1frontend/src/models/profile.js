/**
 * Profile model class
 * @class Profile
 * @property {Number} id
 * @property {String} firstName
 * @property {String} lastName
 * @property {String} photo
 * @property {Date} birth
 * @property {String} occupation
 * @property {String} bio
 * @property {Array} goals
 * @author Justin Chan
 */

export class Profile {
  // eslint-disable-next-line camelcase
  user_id
  // eslint-disable-next-line camelcase
  first_name
  // eslint-disable-next-line camelcase
  last_name
  photo
  // eslint-disable-next-line camelcase
  date_of_birth
  occupation
  bio
  goals

  constructor (id, firstName, lastName, photo, birth, occupation, bio, goals = []) {
    this.user_id = id
    this.first_name = firstName
    this.last_name = lastName
    this.photo = photo
    this.date_of_birth = birth
    this.occupation = occupation
    this.bio = bio
    this.goals = goals
  }

  /**
   * Creates a copy of the profile
   * @param profile
   * @return {null|any}
   * @author Justin Chan
   */
  static copyConstructor (profile) {
    if (profile === null || profile === undefined) return null
    return Object.assign(new Profile(0), profile)
  }
}
