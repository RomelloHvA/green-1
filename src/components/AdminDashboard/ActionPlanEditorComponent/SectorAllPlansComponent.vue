<template>
  <h1> Available plans: </h1>
  <div class="container">
    <div class="row">
      <div class="col-md-3">
        <button class="btn btn-success" @click="handleNewPlan">New Plan</button>
        <action-plan-selector-component :action-plans="editableActionPlans"/>
      </div>
      <div class="col-md-9">
          <action-plan-editor v-if="selectedPlan !== null || currentRouteParam === '0'"
                              :cloned-plan="clonedSelectedPlan"
                              :selected-plan="selectedPlan"
                              @saveOrUpdatePlan="handleSaveOrUpdate"
                              @deletePlan="handleDeleteplan" />
      </div>
    </div>
  </div>

</template>

<script>
import { useRoute, useRouter } from 'vue-router'
import { computed, inject, onBeforeMount, ref, watch } from 'vue'
import ActionPlanSelectorComponent from '@/components/AdminDashboard/ActionPlanEditorComponent/ActionPlanSelectorComponent'
import ActionPlanEditor from '@/components/AdminDashboard/ActionPlanEditorComponent/ActionPlanEditor'
import { useToast } from 'vue-toast-notification'

export default {
  name: 'SectorAllPlansComponent',
  components: {
    ActionPlanEditor,
    ActionPlanSelectorComponent
  },
  props: {
    sectors: ref([]),
    selectedSector: null
  },
  setup (props) {
    const route = useRoute()
    const router = useRouter()
    const currentRouteParam = ref()
    const data = ref()
    const selectedPlan = ref(null)
    const actionPlanService = inject('actionPlanService')
    const editableActionPlans = ref([])
    const isPending = ref(true)
    const error = ref(null)
    const saveResults = ref()
    const toast = useToast()

    const fetchData = async () => {
      const APIResults = await actionPlanService.findAll()
      try {
        editableActionPlans.value = APIResults.allActionPlans.value
        isPending.value = APIResults.isPending.value
        error.value = APIResults.error.value
      } catch (e) {
        console.log(e)
      }
    }
    const handleNewPlan = () => {
      const currentSector = route.params.sector
      const id = 0
      router.push(`/admin_dashboard/action_plans/${currentSector}` + '/' + id)
    }

    onBeforeMount(() => {
      fetchData()
    })

    const handleDeleteplan = async (planId) => {
      console.log('delete clicked')
      const deleteResults = await actionPlanService.deleteActionPlanById(planId, route.params.sector)
      if (deleteResults.success) {
        editableActionPlans.value = editableActionPlans.value.filter(plan => plan.id !== parseInt(planId))
        // Fix front-end side succes msg
        toast.success('Succesfully deleted plan')
        await router.push(`${route.params.sector}`)
      } else {
        toast.error('Something went wrong: ' + deleteResults.error)
      }
    }
    const handleSaveOrUpdate = async (plan) => {
      // Find the index of the plan with the matching ID
      const index = editableActionPlans.value.findIndex((actionPlan) => actionPlan.id === plan.id)

      if (index !== -1) {
        // Plan with matching ID found
        editableActionPlans.value[index].title = plan.title
        editableActionPlans.value[index].description = plan.description
        editableActionPlans.value[index].sdgArray = plan.sdgArray
        editableActionPlans.value[index].sector = plan.sector
        saveResults.value = await actionPlanService.saveActionPlanById(editableActionPlans.value[index], route.params.sector)
        if (saveResults.value.succes) {
          toast.success('Saved succesfully')
        }
      } else {
        // Plan with matching ID not found, it's a new one
        saveResults.value = await actionPlanService.saveActionPlanById(plan, route.params.sector)
        const savedPlan = saveResults.value.savedContent
        // Set correct id for the new plan
        editableActionPlans.value[editableActionPlans.value.length - 1].id = savedPlan.value.id

        // You can add the new plan to the array or perform other actions
        // For example, if you want to add the new plan, you can do something like:
        // editableActionPlans.value.push(plan);
      }
    }

    const clonedSelectedPlan = computed(() => {
      return JSON.parse(JSON.stringify(selectedPlan.value))
    })
    watch(
      () => route.params.id,
      newOption => {
        currentRouteParam.value = newOption
        // console.log(props.sectors.value)
        // Empty paramater
        if (!newOption) {
          selectedPlan.value = null
          return
        }
        // New plan
        if (currentRouteParam.value === '0') {
          selectedPlan.value = {
            id: 0,
            title: 'New plan for editing',
            description: 'Write a description here!',
            sdgArray: [],
            sector: props.selectedSector
          }
          editableActionPlans.value.push(selectedPlan.value)
        } else {
          const isIdInAllActionPlans = editableActionPlans.value.some(actionPlan => actionPlan.id === (parseInt(currentRouteParam.value)))
          // router param doensnt match an id.
          if (!isIdInAllActionPlans) {
            router.push(`${route.params.sector}`)
            // Existing plan
          } else {
            selectedPlan.value = editableActionPlans.value.find((actionPlan) => actionPlan.id === parseInt(currentRouteParam.value))
          }
        }
      }
    )
    return {
      currentRouteParam,
      data,
      selectedPlan,
      clonedSelectedPlan,
      editableActionPlans,
      error,
      isPending,
      handleSaveOrUpdate,
      handleNewPlan,
      handleDeleteplan
    }
  }
}
</script>

<style scoped>

</style>
