import { ref } from 'vue'

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

  async findAll () {
    const allActionPlans = ref([])
    const isPending = ref(true)
    const error = ref(null)

    try {
      const response = await fetch(this.resourcesUrl)
      if (response.ok) {
        allActionPlans.value = await response.json()
        error.value = null
      } else {
        // Handle HTTP errors if the response is not 'ok'
        return new Error('Network response was not ok.')
      }
    } catch (err) {
      error.value = err.message
    } finally {
      isPending.value = false
    }
    return {
      allActionPlans,
      isPending,
      error
    }
  }

  async findActionPlanBySectorId (sectorId) {
    const editableActionPlans = ref([])
    const isPending = ref(true)
    const error = ref(null)

    try {
      const response = await fetch(this.resourcesUrl + '/' + sectorId)
      if (response.ok) {
        editableActionPlans.value = await response.json()
        error.value = null
      } else {
        // Handle HTTP errors if the response is not 'ok'
        return new Error('Network response was not ok.')
      }
    } catch (err) {
      error.value = err.message
    } finally {
      isPending.value = false
    }
    return {
      editableActionPlans,
      isPending,
      error
    }
  }

  async saveActionPlanById (actionPlan, sectorId) {
    const requestUrl = this.resourcesUrl + '/' + sectorId + '/' + actionPlan.id
    const savedContent = ref(null)
    const succes = ref(false)
    const error = ref(null)
    try {
      const response = await fetch(requestUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(actionPlan)
      })
      if (response.ok) {
        savedContent.value = await response.json()
        error.value = null
        succes.value = true
      } else {
        return new Error('Network response was not ok: ' + response.status)
      }
    } catch (err) {
      error.value = err.message
    }
    return { succes, error, savedContent }
  }

  async deleteActionPlanById (actionPlanId, sectorId) {
    const requestUrl = this.resourcesUrl + '/' + sectorId + '/' + actionPlanId
    const success = ref(false)
    const error = ref(null)

    try {
      const response = await fetch(requestUrl, {
        method: 'DELETE'
      })

      if (response.ok) {
        success.value = true
        error.value = null
      } else {
        return new Error('Network response was not ok: ' + response.status)
      }
    } catch (err) {
      error.value = err.message
    }
    return { success, error }
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
