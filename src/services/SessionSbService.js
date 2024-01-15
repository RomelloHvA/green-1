export class SessionSbService {
  RESOURCES_URL // the back-end base url for authentication resources
  _currentToken // the current authentication token of this session
  // to be injected in the authorization header of every outgoing request
  _currentAccount // the account instant of the currently logged on user

  constructor (RESOURCES_URL) {
    this.RESOURCES_URL = RESOURCES_URL
    this._currentToken = null
    this._currentAccount = null
    this.getTokenFromBrowserStorage()
  }

  getTokenFromBrowserStorage () {
    if (this._currentToken != null) return this._currentToken
    this._currentToken = window.sessionStorage.getItem('TOKEN')
    let userAccount = window.sessionStorage.getItem('ACCOUNT')

    if (this._currentToken == null) {
      try {
        if (localStorage !== null) {
          this._currentToken = window.localStorage.getItem('TOKEN')
          userAccount = window.localStorage.getItem('ACCOUNT')
          window.sessionStorage.setItem('TOKEN', this._currentToken)
          window.sessionStorage.setItem('ACCOUNT', userAccount)
        }
      } catch (e) {
        console.error('SessionStorage is not available, using LocalStorage instead.')
      }
    }
    if (userAccount != null) {
      this._currentAccount = JSON.parse(userAccount)
    }
    return this._currentToken
  }

  saveTokenIntoBrowserStorage (token, user) {
    this._currentToken = token
    this._currentAccount = user
    try {
      if (token == null) {
        this._currentAccount = null
        window.sessionStorage.removeItem('TOKEN')
        window.sessionStorage.removeItem('ACCOUNT')

        if (localStorage !== sessionStorage) {
          window.localStorage.removeItem('TOKEN')
          window.localStorage.removeItem('ACCOUNT')
        }
      } else {
        // insert into sessionStorage
        window.sessionStorage.setItem('TOKEN', token)
        window.sessionStorage.setItem('ACCOUNT', JSON.stringify(user))

        // insert into localStorage
        window.localStorage.setItem('TOKEN', token)
        window.localStorage.setItem('ACCOUNT', JSON.stringify(user))
      }
    } catch (e) {
      console.error('SessionStorage is not available, using LocalStorage instead.')
    }
  }

  getUserName () {
    return this._currentAccount ? this._currentAccount.username : ''
  }

  isAuthenticated () {
    return this._currentAccount != null
  }

  async asyncLogin (userName, passWord) {
    let url
    let body
    try {
      url = this.RESOURCES_URL
      body = JSON.stringify({
        username: userName,
        password: passWord
      })
      const response = await fetch(url, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: body
      })
      if (response.ok) {
        const token = response.headers.get('Authorization')
        const user = await response.json()
        this.saveTokenIntoBrowserStorage(token, user)
        return true
      } else {
        if (response.status === 401) {
          console.error('Unauthorized. Token may have expired or invalid.')
        } else {
          console.error('Error in response: ', response)
        }
        return false
      }
    } catch (error) {
      console.error(error)
      this.errorMessage = error.message
    }
  }

  signOut () {
    this.saveTokenIntoBrowserStorage(null, null)
  }

  get currentToken () {
    return this._currentToken
  }

  get currentAccount () {
    return this._currentAccount
  }
}
