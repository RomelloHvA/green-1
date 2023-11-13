import { User } from '@/models/user'

export class UsersAdaptor {
  resourcesUrl;

  constructor (resourcesUrl) {
    this.resourcesUrl = resourcesUrl
    console.log('Create Offers Adaptor for ' + resourcesUrl)
  }

  async fetchJson (url, options = null) {
    const response = await fetch(url, options)
    if (response.ok) {
      return await response.json()
    } else {
      console.log(response, !response.bodyUsed ? await response.text() : '')
      return null
    }
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

    if (user.id === 0) {
      url = `${this.resourcesUrl}/users`
      method = 'POST'
    } else {
      url = `${this.resourcesUrl}/users/${user.id}`
      method = 'PUT'
    }

    const options = {
      method,
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(user)
    }

    return this.fetchJson(url, options)
  }

  async asyncDeleteById (id) {
    const url = `${this.resourcesUrl}/users/${id}`
    const options = {
      method: 'DELETE'
    }

    return this.fetchJson(url, options)
  }
}
