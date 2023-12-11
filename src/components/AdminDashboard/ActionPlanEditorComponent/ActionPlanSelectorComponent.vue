<template>
  <table class="table table-hover">
    <thead>
    <tr>
      <th scope="col">Plan title:</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="plan in filteredActionPlans" :key="plan.id" @click="setSelected(plan)">
      <td>{{ plan.title }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'ActionPlanSelectorComponent',
  props: {
    actionPlans: ref([])
  },
  data () {
    return {
      selectedPlan: null
    }
  },
  methods: {
    setSelected (plan) {
      if (this.selectedPlan === plan) {
        this.selectedPlan = null
      } else {
        this.selectedPlan = plan
      }
      if (this.selectedPlan !== null) {
        const sector = this.$route.params.sector
        const planId = plan.id
        this.$router.push({
          path: `/admin_dashboard/action_plans/${sector}/${planId}`
        })
      }
    }
  },
  computed: {
    filteredActionPlans () {
      // Filter actionPlans based on currentSectorId
      return this.actionPlans.filter(plan => plan.sector.id === parseInt(this.$route.params.sector))
    }
  }
}
</script>

<style scoped>

</style>
