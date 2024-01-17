<template>
  <div class="container-fluid d-flex flex-row justify-content-around m-2 mx-auto" style="width: 80%">
    <button type="button" id="previousSdgButton" class="btn btn-primary btn-lg" @click="this.toPreviousSDG">Previous SDG</button>
    <div class="card" style="width: 25vw; border: 2px solid black;">
      <img :src="this.sdgContent.goal.image" class="card-img-top" alt="sdg-img">
      <div class="card-body">
        <h5 class="card-title">{{ this.sdgContent.info.title }}</h5>
        <p class="card-text text-start">{{ this.sdgContent.info.problem }}</p>
      </div>
    </div>
    <div class="card" style="width: 25vw; border: 2px solid black;">
      <img src="@/assets/img/logos/target.png" class="card-img-top" alt="sdg-img">
      <div class="card-body">
        <h5 class="card-title">Targets</h5>
        <table class="table">
          <thead>
          <th scope="col">#</th>
          <th scope="col">Target</th>
          </thead>
          <tbody>
          <tr class="sdg-targets" v-for="target in this.sdgContent.info.targets" :key="target">
            <th scope="row">{{ this.sdgContent.info.targets.indexOf(target)+1 }}</th>
            <td>{{ target }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="card" style="width: 25vw; border: 2px solid black;">
      <img src="@/assets/img/logos/sdg_goal.jpg" class="card-img-top" alt="sdg-img">
      <div class="card-body">
        <h5 class="card-title">Goals</h5>
        <table class="table">
          <thead>
          <th scope="col">#</th>
          <th scope="col">Goal</th>
          </thead>
          <tbody>
          <tr class="sdg-user-goals" v-for="pGoal in this.sdgContent.info.potentialGoals" :key="pGoal">
            <th scope="row">{{ this.sdgContent.info.potentialGoals.indexOf(pGoal)+1 }}</th>
            <td>{{ pGoal }}</td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
    <button type="button" id="nextSdgButton" class="btn btn-primary btn-lg" @click="this.toNextSDG">Next SDG</button>
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
      // If the id of the sdg is greater than 17 and less than 1, it should show the user that it does not exist
      const intId = parseInt(goalId)
      if (intId < 1 || intId > 17) {
        this.$router.push('/unknown')
      }
      this.loadData(goalId)
    }
  },
  methods: {
    /**
     * Use this method to switch to the previous SDG info page
     */
    toPreviousSDG () {
      const id = parseInt(this.$route.params.id)
      if (id === 1) {
        this.$router.push('/sdg/17')
      } else {
        this.$router.push('/sdg/' + (id - 1))
      }
    },
    /**
     * Use this method to switch to the next SDG info page
     */
    toNextSDG () {
      const id = parseInt(this.$route.params.id)
      if (id === 17) {
        this.$router.push('/sdg/1')
      } else {
        this.$router.push('/sdg/' + (id + 1))
      }
    },
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
