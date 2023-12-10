<template>
  <h1> Available plans: </h1>
  <div class="container">
    <div class="row">
      <div class="col-md-3">
        <action-plan-selector-component :action-plans="allActionPlans"/>
      </div>
      <div class="col-md-9">
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
import ActionPlanSelectorComponent
  from '@/components/AdminDashboard/ActionPlanEditorComponent/ActionPlanSelectorComponent'

export default {
  name: 'SectorAllPlansComponent',
  components: { ActionPlanSelectorComponent },
  props: {
    allActionPlans: ref([])
  },
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
