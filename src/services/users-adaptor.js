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
        const responseText = await response.text()
        console.log(response, !response.bodyUsed ? responseText : '')
        return {
          response: response,
          responseText: responseText
        }
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
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    }
    response = await this.fetchJson(url, options)
    return response
  }

  async asyncFindById (id) {
    const url = `${this.resourcesUrl}/users/${id}`
    const options = {
      method: 'GET',
      headers: {
        'Content-Type': 'application/json'
      }
    }
    return this.fetchJson(url, options)
  }

  async asyncSave (user) {
    let url
    let method

    try {
      if (user.user_id === undefined || user.user_id === 0) {
        url = `${this.resourcesUrl}/authentication/signup`
        method = 'POST'
      } else {
        url = `${this.resourcesUrl}/users/${parseInt(user.user_id)}`
        method = 'PUT'
      }

      const options = {
        method: method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      }

      return this.fetchJson(url, options)
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
