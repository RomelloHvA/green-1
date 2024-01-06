<template>
  <table class="table table-hover">
    <thead>
    <tr>
      <th scope="col">Plan title:</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="plan in filteredActionPlans" :key="plan.id" @click="setSelected(plan)" :class="isPlanSelected(plan.id)">
      <td>{{ plan.title }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script setup>
import { computed, ref, defineProps, watchEffect } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const props = defineProps({
  actionPlans: Array
})
const selectedPlan = ref(null)
const router = useRouter()
const route = useRoute()

const setSelected = (plan) => {
  const sector = route.params.sector
  const planId = plan.id
  // If it is already selected, deselect it and push the route to the sector overview
  if (selectedPlan.value === plan) {
    selectedPlan.value = null
    router.push({
      path: `/admin_dashboard/action_plans/${sector}`
    })
  } else {
    selectedPlan.value = plan
  }
  if (selectedPlan.value !== null) {
    router.push({
      path: `/admin_dashboard/action_plans/${sector}/${planId}`
    })
  }
}

const filteredActionPlans = computed(() => {
  return props.actionPlans.filter(plan => plan.sector.id === parseInt(route.params.sector))
})

const isPlanSelected = (id) => {
  return selectedPlan.value && selectedPlan.value.id === id ? 'table-active' : ''
}

const selectedPlanId = computed(() => {
  return parseInt(route.params.id)
})

watchEffect(() => {
  if (!isNaN(selectedPlanId.value)) {
    const planToSelect = props.actionPlans.find(plan => plan.id === selectedPlanId.value)
    if (planToSelect) {
      selectedPlan.value = planToSelect
    }
  }
})
</script>

<style scoped>
</style>
