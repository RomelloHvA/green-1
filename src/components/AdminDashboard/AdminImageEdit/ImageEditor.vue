<template>
  <div class="row">
    <div v-if="error">
      <AdminErrorComponent :error="error"/>
    </div>
    <section v-else class="w-25 mx-auto">
      <h2 v-if="!this.$route.params.id">Select the page you want to edit the content of</h2>
      <table class="table table-hover border border-dark">
        <thead>
        <tr>
          <th scope="col">{{ isPending ? 'Loading...' : 'Page Title' }}</th>
        </tr>
        </thead>
        <tbody>
        <div v-if="isPending">
          <admin-loader-component/>
        </div>
        <tr v-else v-for="page in allPages" :key="page.pageId" @click="pushIdToRoute(page.pageId)"
            role="button" :class="{ 'table-active': isActive(page.pageId) }">
          <th scope="row">{{ page.pageTitle }}</th>
        </tr>
        </tbody>
      </table>
    </section>
    <div class="col-md-10 mx-auto">
      <ImageChangerComponent :pageId="parseInt(this.$route.params.id)" :images="images" />
    </div>
  </div>
</template>

<script>
/**
 * This component is only responsible for selecting the page where you want to change the image
 * This is component is also built based on ContentComponent made by @author Romello ten Broeke
 * @author Jiaming Yan
 */
import AdminErrorComponent from '@/components/AdminDashboard/AdminErrorComponent'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
import { inject, onBeforeMount, ref, watchEffect } from 'vue'
import router from '@/router'
import ImageChangerComponent from '@/components/AdminDashboard/AdminImageEdit/ImageChangerComponent'
export default {
  name: 'ImageEditor',
  components: { ImageChangerComponent, AdminLoaderComponent, AdminErrorComponent },
  props: {
    pageId: Number
  },
  setup () {
    const imageService = inject('imageService')
    const contentService = inject('contentService')
    const allPages = ref(null)
    const images = ref(null)
    // Should be true because the data is not done loading yet.
    const isPending = ref(true)
    const error = ref(null)
    const basePath = '/admin_dashboard/image'

    // What happens before this component gets mounted.
    onBeforeMount(async () => {
      const APIResults = await contentService.findAllPages()
      const allImages = await imageService.getAllImages()
      // Watches for changes  in the values and updated them accordingly.
      // Without this, it will always wait on a promise and stay empty.
      watchEffect(() => {
        allPages.value = APIResults.pages.value
        images.value = allImages.images.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value
      })
    })
    return { allPages, isPending, error, basePath, images }
  },
  methods: {
    /**
     * Method for pushin the selected pageId to a route.
     * @param pageId to be pushed in the route
     * @author Romello ten Broeke
     */
    pushIdToRoute (pageId) {
      const currentPath = this.$route.path

      // Check if the current path already contains the pageId
      if (currentPath.includes(pageId)) {
        // Empty the path if the pageId is the same
        router.push({ path: this.basePath })
      } else {
        const newPath = this.basePath + `/${pageId}`

        // Push the modified path to the route
        router.push({ path: newPath })
      }
    }
  },
  computed: {
    /**
     * @Returns if the given Id is the same as the Id in the route.params
     * @author Romello ten Broeke
     */
    isActive () {
      return (pageId) => {
        return pageId === parseInt(this.$route.params.id)
      }
    }
  }
}
</script>

<style scoped>

</style>
