export class ActionPlansAdaptor {
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
    console.log('ActionPlansAdaptor.asyncFindAll()...')
    const response = await this.fetchJson(this.resourcesUrl)
    return response
  }

  async asyncFindBySdgId (id) {
    const actionPlansData = await this.fetchJson(this.resourcesUrl + '/sdg/' + parseInt(id))
    return actionPlansData
  }
}
