<template>
  <div>
    <h1>Sector main Editor</h1>
  </div>
  <section>
    <h1> Available sectors: </h1>
    <div class="container">
      <div class="row">
        <!--        Sector selector-->
        <admin-loader-component v-if="isPending"/>
        <div v-else class="col-md-3">
          <table class="table table-hover">
            <thead>
            <tr>
              <th scope="col">Sector Title</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="sector in sectors" :key="sector.id">
              <td>
                {{sector.name}}
              </td>
            </tr>
            </tbody>
          </table>
        </div>
        <!--        Sector Editor-->
        <div class="col-md-9">
          <router-view/>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { inject, onBeforeMount, ref, watch } from 'vue'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
const sectorService = inject('sectorService')
const load = ref(null)
const isPending = ref(true)
const error = ref(null)
const sectors = ref([])

onBeforeMount(async () => {
  const results = await sectorService.asyncFindAll()
  load.value = results.load

  // Use watch to react to changes in entities
  watch(() => results.entities.value, (newEntities) => {
    sectors.value = newEntities
    isPending.value = results.isPending.value
    error.value = results.error.value
  })

  load.value().then(() => {
    // Emit event or perform other actions
  })
})
</script>
<style scoped>

</style>
