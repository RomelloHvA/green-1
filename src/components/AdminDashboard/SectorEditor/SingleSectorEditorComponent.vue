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
    <button class="btn btn-success m-1" @click="saveSector()">Save changes</button>
    <button class="btn btn-danger m-1" @click="deleteSector()">Delete sector</button>
  </div>
</template>

<script setup>
import { defineProps, inject, watchEffect, ref, defineEmits } from 'vue'
import { useRoute } from 'vue-router'
import { useToast } from 'vue-toast-notification'
const sectorService = inject('sectorService')
const route = useRoute()
const toast = useToast()
const emits = defineEmits(['update-sectors', 'delete-sector'])
const props = defineProps({
  sectors: { type: Array }
})

let copySector = ref(null)
const isPending = ref(null)
const error = ref(null)

async function saveSector () {
  isPending.value = true
  error.value = null

  try {
    const result = await sendData()
    watchEffect(() => {
      isPending.value = result.isPending.value
      error.value = result.error.value
    })
    result.load().then(() => {
      emits('update-sectors', copySector, result.entity.value.id)
      toast.success('Sector saved')
    })
  } catch (err) {
    console.error('Error saving sector:', err)
    toast.error('Error saving sector:' + err)
    error.value = err
  } finally {
    isPending.value = false
  }
}

async function deleteSector () {
  isPending.value = true
  error.value = null
  try {
    const result = await sectorService.asyncDeleteById(parseInt(route.params.id))
    watchEffect(() => {
      isPending.value = result.isPending.value
      error.value = result.error.value
    })
    result.load().then(() => {
      emits('delete-sector', route.params.id)
      toast.success('Sector deleted')
    })
  } catch (err) {
    console.error('Error deleting sector:', err)
    toast.error('Error deleting sector:' + err)
    error.value = err
  } finally {
    isPending.value = false
  }
}

async function sendData () {
  return await sectorService.asyncSave(copySector)
}

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
      description: 'Unfound sectors description'
    }
  }
})
</script>

<style scoped>

</style>
