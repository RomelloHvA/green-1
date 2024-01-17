export class User {
  // eslint-disable-next-line camelcase
  user_id
  // eslint-disable-next-line camelcase
  sector_id
  // eslint-disable-next-line camelcase
  first_name
  // eslint-disable-next-line camelcase
  last_name
  email
  securityClearance
  username
  password
  dateOfBirth
  isAdmin

  constructor (userId, sectorId, firstName, lastName, email, securityClearance, username,
    password, dateOfBirth, isAdmin) {
    this.user_id = userId
    this.sector_id = sectorId
    this.first_name = firstName
    this.last_name = lastName
    this.email = email
    this.securityClearance = securityClearance
    this.username = username
    this.password = password
    this.dateOfBirth = dateOfBirth
    this.isAdmin = isAdmin
  }

  async clone () {
    return new User(this.user_id, this.sector_id, this.first_name, this.last_name,
      this.email, this.securityClearance, this.username, this.password, this.dateOfBirth, this.isAdmin)
  }

  static copyConstructor (user) {
    if (!user) return null // Handle the case where 'user' is null or undefined
    const clonedUser = new User()
    return Object.assign(clonedUser, user)
  }

  static equals (user1, user2) {
    if (user1 === null && user2 === null) {
      return true
    }

    if (user1 === null || user2 === null) {
      return false
    }

    // Basic field comparisons
    if (user1.user_id !== user2.user_id || user1.email !== user2.email || user1.username !== user2.username || user1.isAdmin !== user2.isAdmin || user1.password !== user2.password) {
      return false
    }
    // All checks passed, objects are equal
    return true
  }
}
