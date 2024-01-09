<template>
    <div>
        <h1 class="fs-1 fs-sd-2">Select a sector</h1>
        <div class="container">
            <ErrorComponent v-if="error" :error="error" />
            <LoadingComponent v-else-if="isPending" />
            <div class="m-auto row " v-for="(value,key) in sectors" :key="key">
                <div class="col-12 quizButtonSection">
                    <button @click="handleOptionClicked(value)" type="button"  class="btn my-5 quizAnswerButton quizMultipleChoiceButton" > {{ value.name }}</button>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import { ref, inject, onBeforeMount, watchEffect, defineEmits } from 'vue'
import LoadingComponent from '../LoadingComponent.vue'
import ErrorComponent from '../ErrorComponent.vue'

const sectorService = inject('sectorService')
const load = ref(null)
const isPending = ref(true)
const error = ref(null)
const sectors = ref([])

const emits = defineEmits(['sectorSelected'])

onBeforeMount(async () => {
  const results = await sectorService.asyncCustom('all/quizislive', 'GET', null, null)
  load.value = results.load
  watchEffect(() => {
    sectors.value = results.entity.value
    isPending.value = results.isPending.value
    error.value = results.error.value
  })

  load.value().then(() => {
    sectors.value.splice(0, 1)
  })
})

const handleOptionClicked = (sector) => {
  try {
    emits('sectorSelected', sector)
  } catch (error) {
    console.log(error)
  }
}

</script>

<style>
.quizMultipleChoiceButton {
    font-size: 1.5rem;
    width: 80%;
    margin: 20px !important
}

</style>
