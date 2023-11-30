<template>
Select a sector to edit those action plans:
  <form>
    <label for="Select a sector:"></label>
    <select id="sectors" name="sectors" v-model="selectedOption">
      <option v-for="option in options" :value="option" :key="option" @click="pushSelectedToRoute(option)">{{ option }}</option>
    </select>
  </form>
  <SectorAllPlansComponent/>
</template>

<script>
import SectorAllPlansComponent from '@/components/AdminDashboard/ActionPlanEditorComponent/SectorAllPlansComponent'
import router from '@/router'
export default {
  name: 'ActionPlanEditorMain',
  components: { SectorAllPlansComponent },
  data () {
    return {
      options: ['ICT', 'Fashion', 'Engineering'],
      selectedOption: ''
    }
  },
  methods: {
    pushSelectedToRoute (selectedOption) {
      if (selectedOption !== '') {
        router.push(`/admin_dashboard/action_plans/${selectedOption}`)
      }
    }
  }
}
</script>

<style scoped>

</style>
