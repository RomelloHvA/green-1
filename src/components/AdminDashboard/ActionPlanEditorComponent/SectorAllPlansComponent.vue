<template>
  <h1> Available plans: </h1>
  <div class="container">
    <div class="row">
      <div class="col-md-4">
        <section>
          Left side
        </section>
      </div>
      <div class="col-md-8">
        <section>
          Right side
        </section>
      </div>
    </div>
  </div>

</template>

<script>
import { useRoute } from 'vue-router'
import { watch, ref } from 'vue'

export default {
  name: 'SectorAllPlansComponent',
  setup () {
    const route = useRoute()
    const currentRouteParam = ref()
    const data = ref()

    watch(
      () => route.params.sector,
      newOption => {
        currentRouteParam.value = newOption
      }
    )
    return { currentRouteParam, data }
  }
}
</script>

<style scoped>

</style>
