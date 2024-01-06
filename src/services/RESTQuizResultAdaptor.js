// import { QuizResult } from '@/models/quizresult'

export class RESTQuizResultAdaptor {
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
        console.log(response, !response.bodyUsed ? await response.text() : '')
        return null
      }
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }

  async saveResults (results, userId) {
    let url
    let method
    try {
      url = `${this.resourcesUrl}/save/${userId}`
      method = 'POST'

      const options = {
        method,
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(results)
      }
      return this.fetchJson(url, options)
    } catch (error) {
      console.error('Error during fetch:', error)
      return null
    }
  }
}
