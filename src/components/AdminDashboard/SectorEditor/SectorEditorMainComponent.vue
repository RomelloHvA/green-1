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
            <tr v-for="sector in sectors" @click="pushSelectedSectorIdToRoute(sector.id)" :class="{ 'table-active': sector.id === parseInt(route.params.id) }" :key="sector.id">
              <td>
                {{sector.name}}
              </td>
            </tr>
            </tbody>
          </table>
          <button class="btn btn-primary" @click="addNewSector()">Add new sector</button>
        </div>
        <!--        Sector Editor-->
        <div class="col-md-9">
          <router-view :sectors="sectors" @update-sectors="updateSectors" @delete-sector="deleteSector"/>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { inject, onBeforeMount, ref, watch } from 'vue'
import AdminLoaderComponent from '@/components/AdminDashboard/AdminLoaderComponent'
import { useRoute } from 'vue-router'
import router from '@/router'
import Sector from '@/models/Sector'
import { useToast } from 'vue-toast-notification'

const sectorService = inject('sectorService')
const load = ref(null)
const isPending = ref(true)
const error = ref(null)
const sectors = ref([])
const route = useRoute()
const toast = useToast()

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
    toast.success('Sectors loaded')
  })
})

function updateSectors (sectorToUpdate, id) {
  const index = sectors.value.findIndex(sector => sector.id === sectorToUpdate.id)
  sectors.value[index] = sectorToUpdate
  sectors.value[index].id = id
  pushSelectedSectorIdToRoute(id)
}

function deleteSector (id) {
  const index = sectors.value.findIndex(sector => sector.id === id)
  router.push({ path: '/admin_dashboard/sectors' })
  sectors.value.splice(index, 1)
}

function pushSelectedSectorIdToRoute (id) {
  if (id === parseInt(route.params.id)) {
    router.push({ path: '/admin_dashboard/sectors' })
  } else {
    router.push({ path: '/admin_dashboard/sectors/' + id })
  }
}
function addNewSector () {
  // Check if sector with id 0 exists
  // If not create new sector and add to the array
  if (sectors.value.find(sector => sector.id === 0) === undefined) {
    sectors.value.push(new Sector({
      id: 0,
      name: 'New sector',
      description: 'New sector description'
    }))
  }
  pushSelectedSectorIdToRoute(0)
}
</script>
<style scoped>

</style>
