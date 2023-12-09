import { ref } from 'vue'

/**
 * RESTContentAdaptor is responsible for fetching pages and their editable content from a specified resource URL.
 * @author Romello ten Broeke
 */

export class RESTImageAdaptor {
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
      const response = await fetch(this.resourcesUrl)
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
   * @author Romello ten Broeke, Jiaming Yan
   */
  async findImageByPageId (pageId) {
    const editableImage = ref([])
    const isImageSet = ref(false)
    const isPending = ref(true)
    const error = ref(null)

    try {
      const response = await fetch(this.resourcesUrl + '/' + parseInt(pageId) + '/all')

      if (response.ok) {
        editableImage.value = await response.json()
        if (editableImage.value) {
          isImageSet.value = true
        }
        error.value = null
      } else {
        // Handle other HTTP errors if the response is not 'ok'
        return new Error('Network response was not ok.')
      }
    } catch (err) {
      error.value = err.message
    } finally {
      isPending.value = false
    }

    return { editableImage, isPending, error }
  }

  async getAllImages () {
    const images = ref([])
    const isPending = ref(true)
    const error = ref(null)
    try {
      const response = await fetch(this.resourcesUrl)
      if (response.ok) {
        images.value = await response.json()
        error.value = null
      } else {
        return new Error('Network response was not ok.')
      }
    } catch (err) {
      error.value = err.message
    } finally {
      isPending.value = false
    }
    return {
      images,
      isPending,
      error
    }
  }

  async saveImageByPageId (pageId, body) {
    const requestUrl = this.resourcesUrl + '/' + pageId + '/update'
    const savedImage = ref(null)
    const succes = ref(false)
    const error = ref(null)
    try {
      const response = await fetch(requestUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(body)
      })
      if (response.ok) {
        savedImage.value = await response.json()
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
