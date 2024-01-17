<template>
  <div v-show="showItem" class="container sdg-overview slide-in-animation">
    <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 row-cols-lg-6  gx-3 gy-3">
      <SdgCard v-for="goal in sdgArray" :key="goal.id" :content="goal">{{ goal.id }}</SdgCard>
    </div>
  </div>
</template>

<script>
import SdgCard from '@/components/LandingPage/SdgCard.vue'
import { SustainableDevelopmentGoal } from '@/models/SustainableDevelopmentGoal'

/**
 * SdgOverview Component
 * This component is shown on the LandingPage and contains
 * a few rows with  SDG goal cards.
 */
export default {
  name: 'SdgOverview',
  components: { SdgCard },
  props: ['showItem'],
  data () {
    return {
      sdgArray: []
    }
  },
  created () {
    //   Create and store 17 goals in SdgArray
    this.sdgArray = SustainableDevelopmentGoal.createListOfGoals()
  }
}
</script>

<style>
.sdg-overview {
  background-color: #411C97;
  padding: 30px;
  border-radius: 10px;
  margin-top: 20px;
}
</style>
