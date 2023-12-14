<template>
  Select a sector to edit those action plans:
  <SectorDropDownComponent @sectorSelected="pushSelectedToRoute" @sectors="setSectors"/>

  <h1 v-if="this.$route.params.sector === '0'">No sector selected</h1>
  <sector-all-plans-component :sectors="sectors" :selected-sector="selectedSector" v-else-if="this.$route.params.sector !== null && isValidSectorRoute"/>
  <h1 v-else> Please check the url for a valid route</h1>
</template>

<script>
import router from '@/router'
import SectorDropDownComponent from '@/components/adminquiz/SectorDropDownComponent'
import SectorAllPlansComponent from '@/components/AdminDashboard/ActionPlanEditorComponent/SectorAllPlansComponent'

export default {
  name: 'ActionPlanEditorMain',
  components: { SectorAllPlansComponent, SectorDropDownComponent },
  data () {
    return {
      sectors: [],
      selectedSector: null
    }
  },
  methods: {
    setSectors (sectors) {
      this.sectors = sectors
    },
    pushSelectedToRoute (selectedOption) {
      this.selectedSector = selectedOption
      router.push(`/admin_dashboard/action_plans/${selectedOption.id}`)
    }
  },
  computed: {
    // Check if the route param matches the data from backend
    isValidSectorRoute () {
      return this.sectors.some(sector => sector.id === parseInt(this.$route.params.sector))
    }
  }
}
</script>

<style scoped>

</style>
