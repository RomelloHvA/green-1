<template>
  <div>
    <h2 class="mx-0">These are your top sdgs!</h2>
    <!-- The button is displayed if the user is logged in and if the results are not saved -->
    <button v-if="isAuthenticated && !resultsSaved" @click="saveResults" class="btn btn-primary">Save Results</button>
    <!-- The text "Saved" is displayed if the results are saved -->
    <p class="saved-message" v-if="resultsSaved">Saved</p>
  </div>
  <section class="container">
    <div class="row">
      <div style="width: 400px">
        <Doughnut :data="this.data" :options="this.options"/>
      </div>
      <!-- Loop om alle sdg-results toe te voegen -->
      <div v-for="(sdg, index) in sdgData.slice(0, 7)" :key="index" class="col p-0">
        <sdg-card-component :sdg-data="sdg"/>
      </div>
    </div>
  </section>
  <div class="minvh100 container">
    <h1 class="d-flex justify-content-start text-dark my-5 headerText2 row">The results are in! These are your top SDG's!<div class="purpleLine"></div></h1>
    <section class="">
      <div class="d-flex flex-column flex-sm-row  justify-content-center gap-5 topsection">
        <div class="col-sm-5 col dougnet" >
          <Doughnut class="dougnetextra" :data="this.data" :options="this.options"/>
        </div>
        <!--      Loop to add all the sdg-results-->
        <div class="col-sm-6 col d-flex  flexRow gap-3 barss">
          <div v-for="(sdg, index) in sdgData.slice(0, 3)" :key="index" class="col-sm-3 col-4 containerrr">
            <sdg-card-component :sdg-data="sdg" :maxHeight="getMaxBarHeight" :isFromQuizResults="true"/>
          </div>
        </div>
      </div>
    </section>
    <div class="my-5">
      <h1 class="d-flex justify-content-end my-5 mx-auto headerText2 row">Now choose an actionplan! <div class="purpleLine"></div></h1>
      <div v-if="actionPlansAreLoading">
        <LoadingComponentVue  />
      </div>
      <div v-else>
        <h5 v-if="actionPlans.length == 0" class="d-flex justify-content-center my-5 mx-auto headerText2 row">No actionplans found! </h5>
        <div v-else>
          <div class="d-flex row m-auto gap-5 my-5 justify-content-center">
            <ActionPlan v-if="actionPlans[0]" class="col-lg" :id="actionPlans[0].id" :title="actionPlans[0].title" :description="actionPlans[0].description" :sdgs="actionPlans[0].sdgArray" />
            <ActionPlan v-if="actionPlans[1]" class="col-lg" :id="actionPlans[1].id" :title="actionPlans[1].title" :description="actionPlans[1].description" :sdgs="actionPlans[1].sdgArray" />
          </div>
          <div class="d-flex row m-auto gap-5 my-5 justify-content-center">
            <ActionPlan v-if="actionPlans[2]" class="col-lg" :id="actionPlans[2].id" :title="actionPlans[2].title" :description="actionPlans[2].description" :sdgs="actionPlans[2].sdgArray" />
            <ActionPlan v-if="actionPlans[3]" class="col-lg" :id="actionPlans[3].id" :title="actionPlans[3].title" :description="actionPlans[3].description" :sdgs="actionPlans[3].sdgArray" />
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SdgCardComponent from '@/components/quizResultsComponents/sdgCardComponent'
import { ArcElement, Chart as ChartJS, Legend, Tooltip } from 'chart.js'
import { Doughnut } from 'vue-chartjs'
import { options } from '@/assets/testData/chartOptions'
import { sdgData } from '@/assets/testData/sdgTestData'
import ActionPlan from '@/components/quizResultsComponents/ActionPlan'
import LoadingComponentVue from '@/components/LoadingComponent.vue'

ChartJS.register(ArcElement, Tooltip, Legend)

export default {
  name: 'quizResultsView',
  inject: ['sessionService', 'quizResultService', 'actionPlanService'],
  components: {
    SdgCardComponent,
    Doughnut,
    ActionPlan,
    LoadingComponentVue
  },
  computed: {
    isAuthenticated () {
      return this.sessionService.isAuthenticated()
    },
    /**
     * This computed property calculates the height of the bars in the graph.
     * @returns {string} the height of the bars in the graph in px.
     */
    getMaxBarHeight () {
      if (this.isMounted) {
        return document.querySelector('.containerrr').offsetHeight
      }
      return 0
    }
  },
  data () {
    return {
      resultsSaved: false,
      resultId: '',
      sdgArray: '',
      user: '',
      dateOfQuiz: null,
      // This ensures that I can retrieve the userId via the sessionStorge
      account: sessionStorage.getItem('ACCOUNT'),
      data: {
        labels: [],
        datasets: [
          {
            backgroundColor: [],
            data: []
          }
        ]
      },
      options: options,
      sdgData: [],
      isMounted: false,
      actionPlansAreLoading: true,
      actionPlans: [],
      string: '<strong> This is the title </strong> <br> To give a button a circular ring using CSS, you ll want to apply specific styles to the button. Here s an example of how you might do it o give a button a circular ring using CSS, you ll want to apply specific styles to the button. Here s an example of how you might do it',
      top7: []
    }
  },
  /**
   * This created hook sets up all the data by linking the question scores given by the URL to the right SDG .
   * Sorting them and filling in the appropriate variables which are later passed as props to child components.
   * If there is no data or the url is invalid it redirects back to the quiz.
   * @author Romello ten Broeke
   */
  async created () {
    if (this.$route.query.quizanswers) {
      // Catch for errors/wrong answer types
      try {
        const quizanswers = JSON.parse(decodeURIComponent(this.$route.query.quizanswers))
        // sort the scores
        quizanswers.sort((a, b) => {
          // If scores are the same SDG is leading.
          if (b.score === a.score) {
            return a.SDG - b.SDG
          }
          return b.score - a.score
        })
        // Top 7 scores.
        this.top7 = quizanswers.slice(0, 7)
        const scores = this.top7.map(item => item.score)

        // set the corresponding labels and colors through the ID.
        const names = []
        const sdgColors = []
        setNamesAndLabels(this.top7, names, sdgColors)
        // Give the data and labels to the graph .
        this.data.labels = names
        this.data.datasets = [{
          backgroundColor: sdgColors,
          data: scores
        }]

        // Fill in sdgData to pass as a prop later
        for (let i = 0; i < this.top7.length; i++) {
          if (scores[i] === 0) break
          const newTitle = names[i]
          // If the user is logged in this should change the action to a not general one. Will be implemented at a later point
          const newContribution = sdgData.find(sdg => sdg.id === this.top7[i].SDG).generalContribution
          const newDescription = sdgData.find(sdg => sdg.id === this.top7[i].SDG).description
          const newSrc = sdgData.find(sdg => sdg.id === this.top7[i].SDG).src
          const newGifSrc = sdgData.find(sdg => sdg.id === this.top7[i].SDG).gifSrc
          const newColor = sdgData.find(sdg => sdg.id === this.top7[i].SDG).color
          const newId = sdgData.find(sdg => sdg.id === this.top7[i].SDG).id
          this.sdgData.push({
            title: newTitle,
            generalContribution: newContribution,
            description: newDescription,
            src: newSrc,
            gifSrc: newGifSrc,
            color: newColor,
            highestScore: scores[0],
            score: scores[i],
            sdgNumber: newId
          })
        }
      //  Restarts the quiz if the URL cannot be parsed
      } catch (error) {
        this.$router.push('/quiz')
      }

      // retrieve the actionplans
      const sdgs = this.sdgData.map(sdg => sdg.sdgNumber)
      this.actionPlanService.asyncForQuizResults('1', sdgs).then((actionPlans) => {
        this.actionPlans = actionPlans
        this.actionPlansAreLoading = false
      })
    }

    /**
     *
     * @param top7 the top 7 scores and SDG with ids
     * @param names empty names array to fill with the data
     * @param sdgColors empty colors arrray to fill with data
     * This method fills in the arrays with the correct data.
     * @author Romello ten Broeke
     */
    function setNamesAndLabels (top7, names, sdgColors) {
      for (let i = 0; i < top7.length; i++) {
        names.push(sdgData.find(sdg => sdg.id === top7[i].SDG).title)
        sdgColors.push(sdgData.find(sdg => sdg.id === top7[i].SDG).color)
      }
    }
  },
  methods: {
    /**
     * In this methode I retrieve the userId from the session storage and use it to save the result to the current user.
     * Then I use the createJson method which contains all the data that is not stringifyed yet.
     * After that I save it.
     * @author santoshkakkar
     */
    async saveResults () {
      try {
        const currentUser = JSON.parse(this.account).user_id
        const resultJson = this.createJson()
        console.log(resultJson)
        await this.quizResultService.saveResults(resultJson, currentUser)
        this.resultsSaved = true
      } catch (err) {
        console.error('Something went wrong while fetching:', err)
      }
    },
    /**
     * In this method I make an object so it can later be transformed in to Json.
     * @returns the result data
     * @author santoshkakkar
     */
    createJson () {
      const top3 = this.top7.slice(0, 3).map(item => item.SDG)
      const newResult = {
        sdgArray: top3,
        dateOfQuiz: new Date()
      }
      return newResult
    }
  },
  mounted () {
    this.isMounted = true
  }
}
</script>

<style scoped>
.saved-message {
  background-color: #ddd;
  padding-block-end: 6px;
  padding-block-start: 6px;
  padding-bottom: 6px;
  padding-inline-end: 12px;
  padding-inline-start: 12px;
  padding-left: 20px;
  padding-right: 20px;
  padding-top: 6px;
  margin: 5px;
  border-radius: 8px;
  display: inline-block;
}
.headerText2 {
  font-size: 2.6rem;
  font-weight: bold;
  margin-top: 50px;
  color: #292b2e;
}

.purpleLine {
  width: 90%;
  height: 3px;
  margin-top: 10px;
  background-color: #5c258d;
}

.topsection {
  max-height: 35% !important;
  min-height: 35% !important;
}

.startbottom {
  display: flex;
  position: relative;
  bottom: 0;
  align-items: flex-end; /* This will align children to the bottom */
  height: 100%; /* Make sure parent has a defined height */
}

.width13rem{
  max-width: 100px !important;
}

.containerrr {
  position: relative; /* Parent container should have a position other than static */
  height: 300px; /* Example height - the container needs a specific height */
}

.dougnet {
  max-height: 100%;
  max-width: 100%;
}

.dougnetextra {
  max-height: 100%;
  max-width: 100%;
}

@media screen and (max-width: 574px) {
  .topsection {
    max-height: 100% !important;
    min-height: 100% !important;
  }
  .dougnet {
    max-height: 40%;
    justify-content: center;
    margin: auto;
  }
  .width13rem{
    max-width: 100% !important;
  }

  .barss {
    max-height: 15%;
  max-width: 94% !important;

    position: relative;
  }

  .headerText2 {
    font-size: 1.8rem;
  }
}

</style>
