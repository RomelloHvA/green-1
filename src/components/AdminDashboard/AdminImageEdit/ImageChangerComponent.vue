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
        <p>Current image: {{editableImage.imageName}}</p>
        <label for="imageSelect" class="m-2">Select Image:</label>
        <select id="imageSelect" v-model="imageClone.fileName">
          <option value="none">None</option> <!-- New option for 'none' -->
          <option v-for="image in images" :key="image.imageName" :value="image.fileName">{{ image.fileName }}</option>
        </select>
        <!-- Image Preview -->
        <div v-if="imageClone.fileName !== 'none'">
          <img :src="require('@/assets/img/admin-dashboard/images/' + imageClone.fileName)" alt="Selected Image Preview"
               class="img-preview w-25 h-25">
        </div>
        <label for="imageSelect" class="m-2">Change width: </label>
        <input type="number" v-model="imageClone.imageWidth"/>
        <label for="imageSelect" class="m-2">Change height: </label>
        <input type="number" v-model="imageClone.imageHeight"/>
        <button type="button" class="btn btn-success m-1" @click="saveImage"
                :disabled="!isImageChanged(imageClone, editableImage)">
          Save changes
        </button>
        <button type="button" class="btn btn-danger m-1" @click="resetImage"
                :disabled="!isImageChanged(imageClone, editableImage)">
          Reset to original
        </button>
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
    pageId: Number,
    images: Array
  },
  methods: {
    resetImage () {
      this.imageClone.fileName = this.editableImage.fileName
      this.$toast.warning('Restored original')
    },
    isImageChanged (imageA, imageB) {
      return !this.deepEqual(imageA, imageB)
    },
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
    async saveImage () {
      const body = {
        fileName: this.imageClone.fileName
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
    // const images = ref(props.images)
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
    return { editableImage: editableImage, isPending, imageClone: imageClone, error, imageService: imageService, sendData, succes }
  }
}
</script>

<style scoped>
</style>
