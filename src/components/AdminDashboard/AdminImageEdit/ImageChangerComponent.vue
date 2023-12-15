<template>
  <div v-if="error">
    <AdminErrorComponent/>
  </div>
  <section v-else>
    <h2 v-if="!pageId">Select a page to begin editing</h2>
    <!-- If there is a pageId and is still pending show the load screen -->
    <div v-else-if="pageId && isPending">
      <AdminLoaderComponent/>
    </div>
    <form v-else-if="pageId">
      <!-- Image selector -->
      <div class="form-group d-flex flex-row align-items-center" v-if="imageClone.fileName !== 'none' && imageClone.fileName !== null">
        <div class="d-flex flex-column">
          <p>Current image: {{editableImage.imageName}}</p>
          <label for="imageSelect" class="m-2">Select Image:</label>
          <select id="imageSelect" v-model="imageClone.fileName" class="w-50 mx-auto">
            <option value="none">None</option> <!-- New option for 'none' -->
            <option v-for="image in images" :key="image.imageName" :value="image.fileName">{{ image.fileName }}</option>
          </select>
          <!-- Image Preview -->
          <div v-if="imageClone.fileName !== 'none'">
            <img :src="require('@/assets/img/admin-dashboard/images/' + imageClone.fileName)" alt="Selected Image Preview"
                 class="img-preview w-50 h-50 m-2">
          </div>
        </div>
        <div class="d-flex flex-column w-25">
          <!-- Image width changer -->
          <label for="imageSelect" class="m-2">Change width: </label>
          <input type="number" v-model="imageClone.imageWidth"/>
          <!-- Image height changer -->
          <label for="imageSelect" class="m-2">Change height: </label>
          <input type="number" v-model="imageClone.imageHeight"/>
        </div>
        <div class="m-1">
          <!-- Saving the image to the database, disabled if no changes are made -->
          <button type="button" class="btn btn-success m-1" @click="saveImage"
                  :disabled="!isImageChanged(imageClone, editableImage)">
            Save changes
          </button>
          <!-- Resetting the values of the image back to its original state, disabled if no changes are made -->
          <button type="button" class="btn btn-danger m-1" @click="resetImage"
                  :disabled="!isImageChanged(imageClone, editableImage)">
            Reset to original
          </button>
        </div>
      </div>
        <!--    If no image is found -->
      <div class="form-group d-flex flex-row align-items-center" v-else>
        <p>Current image: None</p>
        <label for="imageSelect" class="m-2">Select Image:</label>
        <select id="imageSelect" v-model="imageClone.fileName">
          <option value="none">None</option> <!-- New option for 'none' -->
          <option v-for="image in images" :key="image.imageName" :value="image.fileName">{{ image.fileName }}</option>
        </select>
        <!-- Image Preview when there is an image -->
        <div v-if="imageClone.fileName !== 'none'">
        </div>
        <div class="m-1">
          <!-- Saving the image to the database, disabled if no changes are made -->
          <button type="button" class="btn btn-success m-1" @click="saveImage"
                  :disabled="!isImageChanged(imageClone, editableImage)">
            Save changes
          </button>
          <!-- Resetting the values of the image back to its original state, disabled if no changes are made -->
          <button type="button" class="btn btn-danger m-1" @click="resetImage"
                  :disabled="!isImageChanged(imageClone, editableImage)">
            Reset to original
          </button>
        </div>
      </div>
    </form>
    <div class="mt-3">
      <!--    Preview of the page-->
      <PagePreview v-if="pageId" :pageId="pageId" :imageClone="imageClone"/>
    </div>
  </section>
</template>

<script>
/**
 * This component is responsible for changing the values of the image and saving it to the database
 * This is component is also built based on PageEditorComponent made by @author Romello ten Broeke
 * @author Jiaming Yan
 */
import { inject, onBeforeMount, ref, watch } from 'vue'
import AdminErrorComponent from '@/components/AdminDashboard/AdminErrorComponent'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
import router from '@/router'
import { useToast } from 'vue-toast-notification'
import PagePreview from '@/components/AdminDashboard/AdminImageEdit/PagePreview'

export default {
  name: 'ImageChangerComponent',
  components: {
    PagePreview,
    AdminLoaderComponent,
    AdminErrorComponent
  },
  props: {
    pageId: Number,
    images: Array
  },
  methods: {
    resetImage () {
      this.imageClone = { ...this.editableImage }
      this.$toast.warning('Restored original')
    },
    isImageChanged (imageA, imageB) {
      return !this.deepEqual(imageA, imageB)
    },
    /**
     * This method is used for comparing 2 objects with each other
     * @param obj1
     * @param obj2
     * @return {boolean} true if Object 1 and Object 2 are the same or equal, otherwise false.
     */
    deepEqual (obj1, obj2) {
      if (obj1 === obj2) return true

      if (typeof obj1 !== 'object' || obj1 === null || typeof obj2 !== 'object' || obj2 === null) {
        return false
      }

      const keys1 = Object.keys(obj1)
      const keys2 = Object.keys(obj2)

      if (keys1.length !== keys2.length) {
        return false
      }

      for (const key of keys1) {
        if (!keys2.includes(key) || !this.deepEqual(obj1[key], obj2[key])) {
          return false
        }
      }

      return true
    },
    /**
     * Creating a request to the backend to save the values of the image
     * @return {Promise<void>}
     */
    async saveImage () {
      const body = {
        fileName: this.imageClone.fileName,
        imageWidth: this.imageClone.imageWidth,
        imageHeight: this.imageClone.imageHeight
      }
      try {
        const response = await this.sendData(this.pageId, body)
        console.log(response.succes.value)
        if (response.succes.value) {
          this.$toast.success('Saved successfully')
        } else {
          this.$toast.warning('Couldn\'t save')
        }
      } catch (error) {
        console.log('Error saving image:', error.message)
      }
    }
  },
  setup (props) {
    const imageService = inject('imageService')
    const editableImage = ref([])
    const isPending = ref(true)
    const error = ref(null)
    const imageClone = ref({})
    const pageId = ref(props.pageId)
    const succes = ref(false)
    const $toast = useToast()
    const imageIsSet = ref(false)

    const fetchData = async () => {
      const APIResults = await imageService.findImageByPageId(props.pageId)
      try {
        editableImage.value = APIResults.editableImage.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value

        // Check if editableImage.value is not null before cloning
        imageClone.value = editableImage.value ? { ...editableImage.value } : {}
        succes.value = true
        if (editableImage.value !== null) {
          imageIsSet.value = true
        }
      } catch (e) {
        console.log(e)
        $toast.error('Could not find content for pageId:' + pageId.value)
        await router.push('/admin_dashboard/image')
      }
    }
    const sendData = async (pageId, body) => {
      return await imageService.saveImageByPageId(pageId, body)
    }

    // Only makes a call if the page id is not null
    onBeforeMount(() => {
      if (pageId.value) {
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
    return { editableImage: editableImage, isPending, imageClone: imageClone, error, imageService: imageService, sendData, succes, imageIsSet: imageIsSet }
  }
}
</script>

<style scoped>
</style>
