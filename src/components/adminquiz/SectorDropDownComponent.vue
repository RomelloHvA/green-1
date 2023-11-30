<template>
  <div class="container">
    <div v-if="error">
        <ErrorComponent :error="error" />
    </div>
    <div v-else-if="isPending">
            <LoadingComponent />
    </div>
    <div v-else>
        <div>
            <label for="sectorSelecter" class="form-label h5 m-0">Sector:</label>
            <select class="form-select sectorSelecter" aria-label="Select a type of question" v-model="selectedSector" >
                <option value="" selected disabled>Select a sector</option>
                <option v-for="(value,key) in sectors" :key="key"> {{ value.name }}</option>
            </select>
        </div>
    </div>
  </div>
</template>

<script>
import { ref, inject, onBeforeMount, watchEffect, watch } from 'vue'
import ErrorComponent from '../ErrorComponent.vue'
import LoadingComponent from '../LoadingComponent.vue'
import Sector from '@/models/Sector'

/**
 * This sector dropdown can be used to select a sector
 * The sectors gets fetched from backend and are shown in the dropdown
 * You can give a sector as prop to set the selected sector in the dropdown
 * When you select a sector a event will be emitted with the selected sector as payload
 * @author Marco de Boer
 */
export default {
  name: 'SectorDropDownComponent',
  components: { ErrorComponent, LoadingComponent },
  props: {
    sector: {
      type: Sector,
      required: false
    }
  },
  setup (props, { emit }) {
    const selectedSector = ref('')
    const sectorService = inject('sectorService')
    const load = ref(null)
    const isPending = ref(true)
    const error = ref(null)
    const sectors = ref([])

    onBeforeMount(async () => {
      const results = await sectorService.asyncFindAll()
      load.value = results.load

      watchEffect(() => {
        sectors.value = results.entities.value
        isPending.value = results.isPending.value
        error.value = results.error.value
      })

      load.value().then(() => {
        emit('sectors', sectors.value)
        if (props.sector) {
          selectedSector.value = props.sector.name
        } else {
          selectedSector.value = sectors.value[0].name
        }
      })
    })

    watch(selectedSector, (newValue) => {
      if (newValue) {
        emit('sectorSelected', sectors.value.find(sector => sector.name === newValue))
      }
    })

    return {
      selectedSector,
      error,
      sectors,
      isPending
    }
  }
}
</script>

<style>
    .sectorSelecter:focus {
        outline: none !important;
    }
</style>
