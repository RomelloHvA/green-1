<template>
  <div class="container mt-4">
    <div class="mb-3">
      <label for="title" class="form-label">Title:</label>
      <input type="text" class="form-control" id="title" v-model="title">
    </div>

    <div class="mb-3">
      <label for="description" class="form-label">Description:</label>
      <textarea class="form-control" id="description" v-model="description"></textarea>
    </div>

    <div class="mb-3">
      <label for="numbers" class="form-label">Number Selector:</label>
      <select class="form-select" id="numbers" v-model="selectedNumber">
        <option v-for="num in availableNumbers" :key="num" :value="num">{{ num }}</option>
      </select>
      <button @click="addNumber" class="btn btn-primary" :disabled="selectedNumber === null || numbers.includes(selectedNumber)">Add Number</button>
    </div>

    <ul class="list-group">
      <li v-for="(number, index) in numbers" :key="index" class="list-group-item d-flex justify-content-between align-items-center">
        {{ number }}
        <button @click="removeNumber(index)" class="btn btn-danger btn-sm">Remove</button>
      </li>
    </ul>
  </div>
</template>

<script>

export default {
  data () {
    return {
      title: '',
      description: '',
      selectedNumber: null,
      numbers: [],
      availableNumbers: [1, 2, 3, 4, 5, 6]
    }
  },
  methods: {
    addNumber () {
      if (this.selectedNumber !== null && !this.numbers.includes(this.selectedNumber)) {
        this.numbers.push(this.selectedNumber)
      }
    },
    removeNumber (index) {
      this.numbers.splice(index, 1)
    }
  }
}
</script>

<style scoped>
</style>
