<template>
  <div class="container mt-4">
    <button class="btn btn-success m-1" @click="savePlan()">Save plan</button>
    <button class="btn btn-danger" @click="deletePlan">Delete plan</button>
    <div class="mb-3">
      <label for="title" class="form-label">Title:</label>
      <input type="text" class="form-control" id="title" v-model="cloneActionPlan.title">
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Description:</label>
      <textarea class="form-control" id="description" v-model="cloneActionPlan.description"></textarea>
    </div>

    <div class="mb-3">
      <label for="sdgId" class="form-label">SDG Selector:</label>
      <select class="form-select" id="sdgId" v-model="this.selectedSdG">
        <option v-for="sdg in sdgs" :key="sdg.id" :value="sdg.id">{{ sdg.id + ": " + sdg.title }}</option>
      </select>
      <button @click="addSdgId(this.selectedSdG)" class="btn btn-primary" :disabled="cloneActionPlan.sdgArray === null || cloneActionPlan.sdgArray.includes(this.selectedSdG)">
        Add SDG
      </button>
    </div>
    <ul class="list-group">
      <li v-for="(number, index) in cloneActionPlan.sdgArray" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
        {{ number }}
        <button @click="removeSdgId(index)" class="btn btn-danger btn-sm">Remove</button>
      </li>
    </ul>
  </div>
</template>

<script>
import { sdgData } from '@/assets/testData/sdgTestData'
import { ref, watch } from 'vue'
import { useRoute } from 'vue-router'

export default {
  props: {
    clonedPlan: Object,
    selectedPlan: ref(null)
  },
  data () {
    return {
      sdgs: sdgData,
      selectedSdG: null
    }
  },
  setup (props, { emit }) {
    const cloneActionPlan = ref(JSON.parse(JSON.stringify(props.clonedPlan)))
    const route = useRoute()

    watch(() => props.clonedPlan, (newValue) => {
      // Update the ref to trigger reactivity in the component
      cloneActionPlan.value = JSON.parse(JSON.stringify(newValue))
    })
    const savePlan = () => {
      emit('saveOrUpdatePlan', cloneActionPlan.value)
    }
    const deletePlan = () => {
      emit('deletePlan', route.params.id)
    }

    const addSdgId = (sdgId) => {
      if (cloneActionPlan.value.sdgArray !== null && !cloneActionPlan.value.sdgArray.includes(sdgId)) {
        cloneActionPlan.value.sdgArray.push(sdgId)
      }
    }
    const removeSdgId = (index) => {
      cloneActionPlan.value.sdgArray.splice(index, 1)
    }

    return {
      cloneActionPlan,
      addSdgId,
      removeSdgId,
      savePlan,
      deletePlan
    }
  }
}
</script>

<style scoped>
</style>
