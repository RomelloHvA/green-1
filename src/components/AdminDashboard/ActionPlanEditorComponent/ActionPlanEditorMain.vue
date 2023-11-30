<template>
  Select a sector to edit those action plans:
  <SectorDropDownComponent @sectorSelected="pushSelectedToRoute" @sectors="setSectors"/>
  <SectorAllPlansComponent/>
</template>

<script>
import SectorAllPlansComponent from '@/components/AdminDashboard/ActionPlanEditorComponent/SectorAllPlansComponent'
import router from '@/router'
import SectorDropDownComponent from '@/components/adminquiz/SectorDropDownComponent'

export default {
  name: 'ActionPlanEditorMain',
  components: { SectorDropDownComponent, SectorAllPlansComponent },
  data () {
    return {
      sectors: []
    }
  },
  methods: {
    setSectors (sectors) {
      this.sectors = sectors
    },
    pushSelectedToRoute (selectedOption) {
      router.push(`/admin_dashboard/action_plans/${selectedOption.name}`)
    }
  }
}
</script>

<style scoped>

</style>
