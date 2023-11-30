<template>
  All available plans for: {{this.$route.params.sector}}
</template>

<script>
import { useRoute } from 'vue-router'
import { watch, ref } from 'vue'

export default {
  name: 'SectorAllPlansComponent',
  setup () {
    const route = useRoute()
    const currentRouteParam = ref()

    watch(
      () => route.params.sector,
      newOption => {
        currentRouteParam.value = newOption
      }
    )
    return currentRouteParam
  }
}
</script>

<style scoped>

</style>
