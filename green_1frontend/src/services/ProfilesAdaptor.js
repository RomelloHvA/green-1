import { Profile } from '@/models/profile'

export class ProfilesAdaptor {
  resourcesUrl

  constructor (resourcesUrl) {
    this.resourcesUrl = resourcesUrl
  }

  async fetchJson (url, options = null) {
    try {
      const response = await fetch(url, options)
      if (response.ok) {
        return await response.json()
      } else {
        console.error(response, !response.bodyUsed ? await response.text() : '')
        return null
      }
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async asyncFindAll () {
    const profilesData = await this.fetchJson(this.resourcesUrl)
    return profilesData
  }

  async asyncFindById (id) {
    const profilesData = await this.fetchJson(this.resourcesUrl + '/' + id)
    return Profile.copyConstructor(profilesData)
  }

  async asyncSave (profile) {
    try {
      const response = await this.fetchJson(this.resourcesUrl + '/' + profile.user_id, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(profile)
      })
      return Profile.copyConstructor(response)
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async asyncDeleteById (id) {
    const response = this.fetchJson(this.resourcesUrl + '/' + id,
      {
        method: 'DELETE'
      })
    if (response && response.status === 204) {
      return true
    } else {
      console.error('Error deleting profile: ', response)
      return false
    }
  }
}
