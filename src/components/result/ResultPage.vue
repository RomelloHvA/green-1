<template>
  <div class="container">
    <h2 class="mx-0">These are your top sdgs!</h2>
    <div class="mt-5 mb-5 resultBody">
      <div class="row">
        <div class="col-6">
          <div class="sdgList">
            <div v-for="goal in goals" :key="goal.id" class="spacing">
              <div @click="openActionPlan(goal.sdgId)" class="container sdgGoals">
                <div class="goalTitle">
                  <h6>{{ goal.title }}</h6>
                </div>
                <div>
                  <img :src="getImagePath(goal.image)" alt="goal" class="goalImage">
                </div>
              </div>
            </div>
          </div>
        </div>
        <div class="col-6">
          <div class="actionPlans">
            <template v-if="selectedGoal">
              <RouterView :goals="goals"/>
            </template>
            <template v-else>
              <RouterView class="col-6 action-plan mt-5">
                Click on a goal for action plans.
              </RouterView>
            </template>
          </div>
        </div>
      </div>
    </div>
    <div>
      <button @click="onBack" class="btn-secondary goBackButton">Back to profile</button>
    </div>
  </div>
</template>

<script>

export default {
  name: 'ResultPage',
  inject: ['profileService'],
  data () {
    return {
      loading: true,
      goals: [],
      selectedGoal: null,
      profile: null
    }
  },
  /**
   * Redirects to the results page
   */
  beforeMount () {
    this.$router.push({ path: '/results' })
  },
  /**
   * Fetches the profile and sets the goals
   * @return {Promise<void>}
   */
  async created () {
    try {
      this.profile = await this.profileService.asyncFindById(0)
      // this.profile = this.profile
      this.goals = this.profile.goals
    } catch (error) {
      console.error('Error fetching goals:', error.message)
    } finally {
      this.loading = false
    }
  },
  computed: {
    /**
     * Returns the image path for the given image
     * @return {function(*): *}
     */
    getImagePath () {
      return (image) => require(`@/assets/SDG-Icons/${image}`)
    }
  },
  methods: {
    /**
     * Opens the action plan for the given id
     * @param id
     */
    openActionPlan (id) {
      if (this.selectedGoal === id) {
        // If the selectedGoal is already the same as the given id, clear it
        this.selectedGoal = null
        this.$router.push({ path: '/results' })
      } else {
        // If the selectedGoal is different or null, set it to the given id
        this.selectedGoal = id
        this.$router.push({ path: '/results/' + id })
      }
    },
    /**
     * Redirects to the profile page
     */
    onBack () {
      this.$router.push({ path: '/profile' })
    }
  }
}
</script>

<style scoped>
.resultBody {
}

.goBackButton {
  margin: 0.5rem;
  width: 10rem;
  border-radius: 20px;
  align-self: center;
}

.sdgList {
}

.sdgGoals {
  width: 15rem;
  height: 16rem;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
  margin-top: 1rem;
  border: #6D3FD9 solid 1px;
  border-radius: 20px;
}

.sdgGoals:hover {
  cursor: pointer;
}

.goalImage {
  width: 10rem;
  height: 10rem;
  margin-bottom: 1rem;
}

.goalTitle {
//margin: 1rem; //max-width: 10rem; //max-height: 1rem;
}

h6 {
  height: 1rem;
  font-weight: bold;
}

.spacing {
  margin: 1rem;
}

.actionPlans {
}

</style>
