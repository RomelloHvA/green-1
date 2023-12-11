export class User {
  // eslint-disable-next-line camelcase
  user_id
  email
  username
  isAdmin
  // eslint-disable-next-line camelcase
  user_goal
  password
  // eslint-disable-next-line camelcase
  security_clearance
  bio
  occupation
  // eslint-disable-next-line camelcase
  date_of_birth
  postalcode

  // eslint-disable-next-line camelcase
  constructor (user_id, email, username, isAdmin, user_goal, password) {
    // eslint-disable-next-line camelcase
    this.user_id = user_id
    this.email = email
    this.username = username
    this.isAdmin = isAdmin
    // eslint-disable-next-line camelcase
    this.user_goal = user_goal
    this.password = password
  }

  async clone () {
    return new User(this.user_id, this.email, this.username, this.isAdmin, this.user_goal, this.password)
  }

  static copyConstructor (user) {
    if (!user) return null // Handle the case where 'user' is null or undefined
    const clonedUser = new User(user.user_id, user.email, user.username, user.isAdmin, user.user_goal, user.password)
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
