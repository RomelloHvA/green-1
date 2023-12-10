<template>
  Select a sector to edit those action plans:
  <SectorDropDownComponent @sectorSelected="pushSelectedToRoute" @sectors="setSectors"/>

  <h1 v-if="this.$route.params.sector === '1'">No sector selected</h1>
  <router-view v-else-if="this.$route.params.sector !== null && isValidSectorRoute"/>
  <h1 v-else> Please check the url for a valid route</h1>
</template>

<script>
import router from '@/router'
import SectorDropDownComponent from '@/components/adminquiz/SectorDropDownComponent'

export default {
  name: 'ActionPlanEditorMain',
  components: { SectorDropDownComponent },
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
