<template>
  <div>
    <h1>Sector Editor</h1>
    <h2 v-if="copySector.id === null ">Can't find matching sector with id: {{route.params.id}}</h2>
    <div class="form-group">
      <label for="title">Title:</label>
      <input type="text" class="form-control" id="title" v-model="copySector.name" placeholder="Enter title">
    </div>

    <div class="form-group">
    <label for="description">Description:</label>
    <textarea class="form-control" id="description" v-model="copySector.description" placeholder="Enter description"></textarea>
  </div>
    <button class="btn btn-primary" @click="logSectors()">Log sectors</button>
  </div>
</template>

<script setup>
import { defineProps, watchEffect } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const props = defineProps({
  sectors: { type: Array }
})

function logSectors () {
  console.log(props.sectors)
  console.log(copySector)
}
let copySector = null

// Watch the route for changes and update the copySector
watchEffect(() => {
  const newSectorId = parseInt(route.params.id)
  const originalSector = props.sectors.find(sector => sector.id === newSectorId)

  if (originalSector !== undefined) {
    copySector = { ...originalSector }
    // Id could not be found in the sectors array
  } else {
    copySector = {
      id: null,
      name: 'Unfound Sector id',
      description: 'Unfound sector description'
    }
  }
})
</script>

<style scoped>

</style>
