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
      <div class="form-group" v-for="content in contentClone" :key="content.contentId">
        <!-- Deployed text -->
        <label :for="'Textarea_' + content.contentId">{{ content.contentTitle }}</label>
        <textarea class="form-control" :id="'Textarea_' + content.contentId" rows="3" disabled
                  :placeholder="findContentById(content.contentId).contentDutch"></textarea>
        <!-- Editable Text to be deployed -->
        <label :for="'Textarea_' + content.contentId">CONCEPT Text for {{ content.contentTitle }}</label>
        <textarea class="form-control" :id="'Textarea_' + content.contentId" rows="3"
                  v-model="content.contentConcept"></textarea>
        <button type="button" class="btn btn-success m-1" @click="saveNewContent(content.contentId, content, 'true')">Save concept</button>
        <button type="button" class="btn btn-info m-1" @click="saveNewContent(content.contentId, content, 'false')">Deploy concept</button>
        <button type="button" class="btn btn-danger m-1" @click="resetContentToDeployed(content)"
                :disabled="!isContentChanged(content.contentConcept, findContentById(content.contentId).contentDutch)">
          Reset to original
        </button>
        <button type="button" class="btn btn-danger mx-1" @click="resetContentToConcept(content)"
                :disabled="!isContentChanged(content.contentConcept, findContentById(content.contentId).contentConcept)">
          Reset to saved concept
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
  name: 'PageEditorComponent',
  components: {
    AdminLoaderComponent,
    AdminErrorComponent
  },
  props: {
    pageId: Number
  },
  methods: {
    resetContentToDeployed (content) {
      const originalContent = this.findContentById(content.contentId)
      content.contentConcept = originalContent.contentDutch
      this.$toast.warning('Restored original')
    },
    resetContentToConcept (content) {
      const originalContent = this.findContentById(content.contentId)
      content.contentConcept = originalContent.contentConcept
      this.$toast.warning('Restored concept')
    },
    findContentById (id) {
      if (this.editableContent) {
        return this.editableContent.find(content => content.contentId === id)
      } else {
        return ''
      }
    },
    isContentChanged (contentA, contentB) {
      return contentA !== contentB
    },
    /**
     * @param id id of the content to be saved.
     * @param content content to be saved.
     * @param urlParameter is if only the concept should be saved.
     */
    async saveNewContent (id, content, urlParameter) {
      const indexOfContent = this.editableContent.findIndex(content => content.contentId === id)
      if (indexOfContent !== -1) {
        // Only overwrites the concept in the frontend and sends an API request
        if (urlParameter === 'true') {
          this.editableContent[indexOfContent].contentConcept = content.contentConcept
          // Overwrites Dutch text and concept of the original
        } else {
          this.editableContent[indexOfContent].contentConcept = content.contentConcept
          // this.editableContent[indexOfContent].contentDutch = content.contentConcept // temporary edit
          this.editableContent[indexOfContent].contentEnglish = content.contentConcept
        }
        try {
          // Makes the call to the API to also save it in the backend.
          const APIResult = await this.sendData(this.editableContent[indexOfContent], urlParameter)
          console.log(APIResult.succes.value)

          if (APIResult.succes.value) {
            this.$toast.success('Saved successfully')
          } else {
            this.$toast.warning('Couldn\'t save')
          }
        } catch (error) {
          console.error('Error saving content:', error.message)
        }
      }
    }
  },
  setup (props) {
    const contentService = inject('contentService')
    const editableContent = ref([])
    const isPending = ref(true)
    const error = ref(null)
    const contentClone = ref({})
    const pageId = ref(props.pageId)
    const succes = ref(false)
    const $toast = useToast()

    const fetchData = async () => {
      const APIResults = await contentService.findContentByPageId(props.pageId)
      try {
        editableContent.value = APIResults.editableContent.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value
        // Clones all the content to a cloned object so the original stays
        contentClone.value = Object.fromEntries(editableContent.value.map(item => [item.contentId, { ...item }]))
        succes.value = true
      } catch (e) {
        console.log(e)
        $toast.error('Could not find content for pageId:' + pageId.value)
        await router.push('/admin_dashboard/content')
      }
    }
    const sendData = async (content, urlParamater) => {
      return await contentService.saveContentById(content, urlParamater)
    }

    // Only makes a call if the page id is not null
    onBeforeMount(() => {
      if (pageId.value) {
        fetchData()
      } else {
        // Reset editableContent if pageId is null
        editableContent.value = null
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
          editableContent.value = null
        }
      }
    )
    return { editableContent, isPending, error, contentClone, contentService, sendData, succes }
  }
}
</script>

<style scoped>
</style>
