import { ref } from 'vue'

/**
 * RESTContentAdaptor is responsible for fetching pages and their editable content from a specified resource URL.
 * @author Romello ten Broeke
 */

export class RESTContentAdaptor {
  resourcesUrl

  constructor (resourcesUrl) {
    this.resourcesUrl = resourcesUrl
  }

  /**
   * Fetches all available pages from the specified resources URL.
   * @returns {Object} An object containing ref objects for pages, isPending, and error.
   * @author Romello ten Broeke
   */

  async findAllPages () {
    const pages = ref([])
    const isPending = ref(true)
    const error = ref(null)
    try {
      const response = await fetch(this.resourcesUrl + '/all')
      if (response.ok) {
        pages.value = await response.json()
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
      pages,
      isPending,
      error
    }
  }

  /**
   * Fetches all available content for the given pageId from the specified resources URL.
   * @returns {Object} An object containing ref objects for pages, isPending, and error.
   * @author Romello ten Broeke
   */
  async findContentByPageId (pageId) {
    const editableContent = ref([])
    const isPending = ref(true)
    const error = ref(null)
    try {
      const response = await fetch(this.resourcesUrl + '/content/' + parseInt(pageId) + '/all')
      if (response.ok) {
        editableContent.value = await response.json()
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
      editableContent,
      isPending,
      error
    }
  }

  async saveContentById (content, urlParameter) {
    const requestUrl = this.resourcesUrl + '/content/' + content.contentId + '/' + urlParameter
    const savedContent = ref(null)
    const succes = ref(false)
    const error = ref(null)
    try {
      const response = await fetch(requestUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(content)
      })
      if (response.ok) {
        savedContent.value = await response.json()
        error.value = null
        succes.value = true
      } else {
        throw new Error('Network response was not ok: ' + response.status)
      }
    } catch (err) {
      error.value = err.message
    }
    console.log(succes.value + ' succes value')
    console.log(error.value + ' error value')
    return { succes, error }
  }
}
