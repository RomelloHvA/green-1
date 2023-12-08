<template>
  <div v-if="error">
    <AdminErrorComponent :error="error"/>
  </div>
  <section v-else>
    <h2 v-if="!pageId">Select a page to begin editing</h2>
    <!-- If there is a pageId and is still pending show the load screen -->
    <div v-if="pageId && isPending">
      <AdminLoaderComponent/>
    </div>
    <form v-else-if="pageId">
      <div class="form-group">
        <p>Current image Id: {{editableImage.imageId}}</p>
        <label for="imageSelect">Select Image:</label>
        <select id="imageSelect" v-model="selectedImage" @change="updateImagePath">
          <option v-for="image in images" :key="image.imageId" :value="image.imagePath">{{ image.imageName }}</option>
        </select>
      </div>
    </form>
  </section>
</template>

<script>
import { inject, onBeforeMount, ref, watch } from 'vue'
import AdminErrorComponent from '@/components/AdminDashboard/AdminErrorComponent'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
import router from '@/router'
import { useToast } from 'vue-toast-notification'

export default {
  name: 'ImageChangerComponent',
  components: {
    AdminLoaderComponent,
    AdminErrorComponent
  },
  props: {
    pageId: Number
  },
  methods: {
    resetContentToDeployed (content) {
      const originalContent = this.findImageById(content.contentId)
      content.contentConcept = originalContent.contentDutch
      this.$toast.warning('Restored original')
    },
    resetContentToConcept (content) {
      const originalContent = this.findImageById(content.contentId)
      content.contentConcept = originalContent.contentConcept
      this.$toast.warning('Restored concept')
    },
    findImageById (id) {
      if (this.editableImage) {
        return this.editableImage.find(content => content.contentId === id)
      } else {
        return ''
      }
    },
    isContentChanged (contentA, contentB) {
      return contentA !== contentB
    }
    /**
     * @param id id of the content to be saved.
     * @param content content to be saved.
     * @param urlParameter is if only the concept should be saved.
     */
    // async saveNewContent (id, content, urlParameter) {
    //   const indexOfContent = this.editableContent.findIndex(content => content.contentId === id)
    //   if (indexOfContent !== -1) {
    //     // Only overwrites the concept in the frontend and sends an API request
    //     if (urlParameter === 'true') {
    //       this.editableContent[indexOfContent].contentConcept = content.contentConcept
    //       // Overwrites Dutch text and concept of the original
    //     } else {
    //       this.editableContent[indexOfContent].contentConcept = content.contentConcept
    //       // this.editableContent[indexOfContent].contentDutch = content.contentConcept // temporary edit
    //       this.editableContent[indexOfContent].contentEnglish = content.contentConcept
    //     }
    //     try {
    //       // Makes the call to the API to also save it in the backend.
    //       const APIResult = await this.sendData(this.editableContent[indexOfContent], urlParameter)
    //       console.log(APIResult.succes.value)
    //
    //       if (APIResult.succes.value) {
    //         this.$toast.success('Saved successfully')
    //       } else {
    //         this.$toast.warning('Couldn\'t save')
    //       }
    //     } catch (error) {
    //       console.error('Error saving content:', error.message)
    //     }
    //   }
    // }
  },
  setup (props) {
    const imageService = inject('imageService')
    const editableImage = ref([])
    const images = ref(null)
    const isPending = ref(true)
    const error = ref(null)
    const imageClone = ref({})
    const pageId = ref(props.pageId)
    const succes = ref(false)
    const $toast = useToast()

    const fetchData = async () => {
      const APIResults = await imageService.findImageByPageId(props.pageId)
      try {
        editableImage.value = APIResults.editableImage.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value
        // Clones all the content to a cloned object so the original stays
        imageClone.value = { ...editableImage.value }
        succes.value = true
      } catch (e) {
        console.log(e)
        $toast.error('Could not find content for pageId:' + pageId.value)
        await router.push('/admin_dashboard/image')
      }
    }
    const sendData = async (content, urlParamater) => {
      return await imageService.saveContentById(content, urlParamater)
    }

    const getAllImages = async () => {
      const response = await imageService.getAllImages()
      console.log(response)
    }

    // Only makes a call if the page id is not null
    onBeforeMount(() => {
      if (pageId.value) {
        getAllImages()
        fetchData()
      } else {
        // Reset editableContent if pageId is null
        editableImage.value = null
      }
    })

    // Watches if the pageId changes before making a new call to the Backend
    // Only makes a call if the page id is not null
    watch(
      () => props.pageId,
      (newPageId, oldPageId) => {
        if (newPageId && newPageId !== oldPageId) {
          fetchData()
        } else {
          // Reset editableContent if pageId is null
          editableImage.value = null
        }
      }
    )
    return { editableImage: editableImage, isPending, imageClone: imageClone, images: images, error, imageService: imageService, sendData, succes }
  }
}
</script>

<style scoped>
</style>
