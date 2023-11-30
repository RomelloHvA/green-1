<template>
Select a sector to edit those action plans:
    <SectorDropDownComponent @sectorSelected="pushSelectedToRoute"/>
  <SectorAllPlansComponent/>
</template>

<script>
import SectorAllPlansComponent from '@/components/AdminDashboard/ActionPlanEditorComponent/SectorAllPlansComponent'
import router from '@/router'
import SectorDropDownComponent from '@/components/adminquiz/SectorDropDownComponent'
import { ref } from 'vue'

export default {
  name: 'ActionPlanEditorMain',
  components: { SectorDropDownComponent, SectorAllPlansComponent },
  data () {
    return {
      options: ['ICT', 'Fashion', 'Engineering'],
      selectedOption: ref()
    }
  },
  methods: {
    pushSelectedToRoute (selectedOption) {
      if (selectedOption.name !== 'none' || selectedOption.name !== '') {
        router.push(`/admin_dashboard/action_plans/${selectedOption.name}`)
      }
    }
  }
}
</script>

<style scoped>

</style>
