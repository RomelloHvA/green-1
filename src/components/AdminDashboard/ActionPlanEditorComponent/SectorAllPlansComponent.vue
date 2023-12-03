<template>
  <h1> Valid route </h1>
</template>

<script>
import { useRoute } from 'vue-router'
import { watch, ref } from 'vue'

export default {
  name: 'SectorAllPlansComponent',
  setup () {
    const route = useRoute()
    const currentRouteParam = ref()
    const validRouteParam = 'Technology'
    let isValidRoute = false

    watch(
      () => route.params.sector,
      newOption => {
        currentRouteParam.value = newOption
        isValidRoute = currentRouteParam.value === validRouteParam
      }
    )
    return { currentRouteParam, isValidRoute, validRouteParam }
  }
}
</script>

<style scoped>

</style>
