<template>
  <table class="table">
    <thead>
    <tr>
      <th scope="col">Plan title:</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="plan in filteredActionPlans" :key="plan.id">
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
