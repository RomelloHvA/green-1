<template>
  <h1> Available plans: </h1>
  <div class="container">
    <div class="row">
      <div class="col-md-3">
        <action-plan-selector-component :action-plans="allActionPlans"/>
      </div>
      <div class="col-md-9">
        <section>
          <action-plan-editor :selected-action-plan="selectedPlan"/>
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
import ActionPlanEditor from '@/components/AdminDashboard/ActionPlanEditorComponent/ActionPlanEditor'

export default {
  name: 'SectorAllPlansComponent',
  components: {
    ActionPlanEditor,
    ActionPlanSelectorComponent
  },
  props: {
    allActionPlans: ref([])
  },
  data () {
    return {
      selectedPlan: ref(null)
    }
  },
  setup () {
    const route = useRoute()
    const currentRouteParam = ref()
    const data = ref()

    watch(
      () => route.params.id,
      newOption => {
        currentRouteParam.value = newOption
        if (currentRouteParam.value === 0) {
          this.selectedPlan = {
            title: 'nothing',
            description: '',
            sdgId: []
          }
        }
      }
    )
    return {
      currentRouteParam,
      data
    }
  }
}
</script>

<style scoped>

</style>
