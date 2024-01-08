export class User {
  userId
  sectorId
  firstName
  lastName
  email
  securityClearance
  username
  password
  dateOfBirth

  constructor (userId, sectorId, firstName, lastName, email, securityClearance, username,
    password, dateOfBirth) {
    this.userId = userId
    this.sectorId = sectorId
    this.firstName = firstName
    this.lastName = lastName
    this.email = email
    this.securityClearance = securityClearance
    this.username = username
    this.password = password
    this.dateOfBirth = dateOfBirth
  }

  async clone () {
    return new User(this.userId, this.sectorId, this.firstName, this.lastName,
      this.email, this.securityClearance, this.username, this.password, this.dateOfBirth)
  }

  static copyConstructor (user) {
    if (!user) return null // Handle the case where 'user' is null or undefined
    const clonedUser = new User(
      user.user_id,
      user.sector_id,
      user.first_name,
      user.last_name,
      user.email,
      user.security_clearance,
      user.username,
      user.password,
      user.date_of_birth
    )
    return clonedUser
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
