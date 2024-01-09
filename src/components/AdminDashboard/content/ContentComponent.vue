<template>
  <div>
    <div class="row">
      <div class="col-md-2">
        <div v-if="error">
          <admin-error-component :error="error"/>
        </div>
        <section v-else>
          <h2 v-if="!this.$route.params.id">Select the page you want to edit the content of</h2>
          <table class="table table-hover">
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
      </div>

      <div class="col-md-10">
        <PageEditorComponent :pageId="parseInt(this.$route.params.id)"/>
      </div>
    </div>
  </div>
</template>

<script>

import { inject, onBeforeMount, ref, watchEffect } from 'vue'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
import AdminErrorComponent from '@/components/AdminDashboard/AdminErrorComponent'
import router from '@/router'
import PageEditorComponent from '@/components/AdminDashboard/content/PageEditorComponent'

export default {
  name: 'ContentComponent',
  components: { PageEditorComponent, AdminErrorComponent, AdminLoaderComponent },
  /**
   * This hook is called before loading this component. It is in charge of getting all the necesseray data.
   * @returns allPages an array of the pages with ids,
   *          isPending boolean for if the loading is done.
   *          error: the error message if something went wrong. Is null when all is good.
   *          basePath: base path of this route.
   * @author Romello ten Broeke
   */
  setup () {
    const contentService = inject('contentService')
    const allPages = ref(null)
    // Should be true because the data is not done loading yet.
    const isPending = ref(true)
    const error = ref(null)
    const basePath = '/admin_dashboard/content'

    // What happens before this component gets mounted.
    onBeforeMount(async () => {
      const APIResults = await contentService.findAllPages()
      // Watches for changes  in the values and updated them accordingly.
      // Without this, it will always wait on a promise and stay empty.
      watchEffect(() => {
        allPages.value = APIResults.pages.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value
      })
    })
    return { allPages, isPending, error, basePath }
  },
  methods: {
    /**
     * Method for pushing the selected pageId to a route.
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
