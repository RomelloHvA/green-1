<template>
  <div class="container mt-4">
    <div class="mb-3">
      <label for="title" class="form-label">Title:</label>
      <input type="text" class="form-control" id="title" v-model="localData.title">
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Description:</label>
      <textarea class="form-control" id="description" v-model="localData.description"></textarea>
    </div>

    <div class="mb-3">
      <label for="numbers" class="form-label">SDG Selector:</label>
      <select class="form-select" id="numbers" v-model="localData.selectedSdg">
        <option v-for="sdg in sdgs" :key="sdg.id" :value="sdg.id">{{ sdg.id + " " + sdg.title }}</option>
      </select>
      <button @click="addSdgId" class="btn btn-primary" :disabled="localData.selectedSdg === null || pickedSdgs.includes(localData.selectedSdg)">Add SDG</button>
    </div>

    <ul class="list-group">
      <li v-for="(number, index) in pickedSdgs" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
        {{ number }}
        <button @click="removeSdgId(index)" class="btn btn-danger btn-sm">Remove</button>
      </li>
    </ul>
  </div>
</template>

<script>

import { sdgData } from '@/assets/testData/sdgTestData'
import { ref } from 'vue'

export default {
  props: {
    selectedActionPlan: ref([])
  },
  data () {
    return {
      localData: {
        title: '',
        description: '',
        selectedSdg: []
      },
      pickedSdgs: [],
      sdgs: sdgData
    }
  },
  methods: {
    addSdgId () {
      if (this.localData.selectedSdg !== null && !this.pickedSdgs.includes(this.localData.selectedSdg)) {
        this.pickedSdgs.push(this.localData.selectedSdg)
      }
    },
    removeSdgId (index) {
      this.pickedSdgs.splice(index, 1)
    },
    notifyParent () {
      // Emit an event to notify the parent component about the changes
      this.$emit('update-data', this.localData)
    }
  },
  setup () {
  }
}
</script>

<style scoped>
</style>
