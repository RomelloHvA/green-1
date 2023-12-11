<template>
<div class="container row-cols-1 row-cols-2-md" style="text-align: left">
  <div class="row">
    <div class="col-sm-6">
      <div class="row"><h1>{{ this.sdgContent.info.title }}</h1></div>
      <div class="row"><h2>Problem:</h2></div>
      <div class="row">{{ this.sdgContent.info.problem }}</div>
      </div>
    <div class="col-6"><img class="img-fluid sizing" :src="this.sdgContent.goal.image" alt="foto"></div>
  </div>
  <div class="row"><h2>Targets</h2>
      <ol>
        <li class="sdg-targets" v-for="target in this.sdgContent.info.targets" :key="target">{{target}}</li>
      </ol>
  </div>
  <div class="row"><h2>Goals</h2>
      <ul>
        <li class="sdg-user-goals" v-for="pGoal in this.sdgContent.info.potentialGoals" :key="pGoal">{{pGoal}}</li>
      </ul>
  </div>
</div>
</template>

<script>
import { SustainableDevelopmentGoal } from '@/models/SustainableDevelopmentGoal'

/**
 * Sustainable Development Goal Information Page
 * On this page, users can read in-depth information about an SDG
 */
export default {
  name: 'SdgInfoPage',
  props: ['sdg'],
  data () {
    return {
      sdgContent: SustainableDevelopmentGoal.getInfo(1)
    }
  },
  watch: {
    /**
     * watch for path update and load correct SDG content
     * @param route current route
     */
    '$route' (route) {
      const goalId = route.params.id
      this.loadData(goalId)
    }
  },
  methods: {
    /**
     * Loads additional data from an SDG by id
     * **TEMPORARY** set id=1 to prevent /sdg from crashing
     * @param id of an SDG
     */
    loadData (id) {
      if (id === undefined) id = 1
      if (id < 1 || id > 17) id = 1
      this.sdgContent = SustainableDevelopmentGoal.getInfo(id)
    }
  },
  /**
   * Once created, load data
   */
  created () {
    this.loadData(this.$route.params.id)
  }
}
</script>

<style scoped>
.sizing {
  width: 400px;
  height: auto;
}
</style>
