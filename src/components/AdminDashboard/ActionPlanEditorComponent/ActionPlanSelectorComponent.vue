<template>
  <table class="table">
    <thead>
    <tr>
      <th scope="col">Plan title:</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="plan in actionPlans" :key="plan.id">
      <td>{{ plan.title }}</td>
    </tr>
    </tbody>
  </table>
</template>

<script>
export default {
  name: 'ActionPlanSelectorComponent',
  props: {
    actionPlans: []
  }
}
</script>

<style scoped>

</style>
