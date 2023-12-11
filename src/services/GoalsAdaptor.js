import { Goal } from '@/models/goal'

export class GoalsAdaptor {
  resourcesUrl

  constructor (resourcesUrl) {
    this.resourcesUrl = resourcesUrl
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
    console.log('GoalsAdaptor.asyncFindAll()...')
    const response = await this.fetchJson(this.resourcesUrl)
    return response
  }

  async asyncFindById (id) {
    const goalsData = await this.fetchJson(this.resourcesUrl + '/' + parseInt(id))
    return goalsData
  }

  async asyncSave (goal) {
    if (!goal.id || goal.id === 0) {
      const response = await this.fetchJson(this.resourcesUrl, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(goal)
      })
      return Goal.copyConstructor(response)
    } else {
      const response = await this.fetchJson(this.resourcesUrl + '/' + goal.id, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(goal)
      })
      return Goal.copyConstructor(response)
    }
  }

  async asyncDeleteById (id) {
    const response = await this.fetchJson(this.resourcesUrl + '/' + id,
      {
        method: 'DELETE'
      })
    if (response && response.status === 204) {
      console.log('Goal deleted succesfully.')
      return true
    } else {
      console.log('Error deleting goal: ', response)
      return false
    }
  }
}
