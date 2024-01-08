import { User } from '@/models/user'

export class UsersAdaptor {
  resourcesUrl

  constructor (resourcesUrl) {
    this.resourcesUrl = resourcesUrl
    console.log('Create Offers Adaptor for ' + resourcesUrl)
  }

  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      if (response.ok) {
        return await response.json()
      } else {
        console.log(response, !response.bodyUsed ? await response.text() : '')
        return null
      }
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async asyncFindByName (username) {
    const url = `${this.resourcesUrl}/users/name`
    const options = {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: username
    }
    return this.fetchJson(url, options)
  }

  async asyncFindAll () {
    let response = []
    const url = `${this.resourcesUrl}/users/all`
    response = await this.fetchJson(url)
    return response.map(user => User.copyConstructor(user))
  }

  async asyncFindById (id) {
    const url = `${this.resourcesUrl}/users/${id}`
    return this.fetchJson(url)
  }

  async asyncSave (user) {
    let url
    let method

    try {
      if (user.user_id === 0) {
        url = `${this.resourcesUrl}/users`
        method = 'POST'
      } else {
        url = `${this.resourcesUrl}/users/${parseInt(user.user_id)}`
        method = 'PUT'
      }

      const options = {
        method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      }

      return User.copyConstructor(this.fetchJson(url, options))
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async asyncUpdateProfile (data, id) {
    let url
    let method
    try {
      url = `${this.resourcesUrl}/users/profile/${parseInt(id)}`
      method = 'PUT'
      const options = {
        method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: data
      }

      return this.fetchJson(url, options)
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async asyncDeleteById (id) {
    const url = `${this.resourcesUrl}/users/${id}`
    const options = {
      method: 'DELETE'
    }

    return this.fetchJson(url, options)
  }
}
