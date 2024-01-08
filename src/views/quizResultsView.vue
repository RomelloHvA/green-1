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
</template>

<script>
import SdgCardComponent from '@/components/quizResultsComponents/sdgCardComponent'
import { ArcElement, Chart as ChartJS, Legend, Tooltip } from 'chart.js'
import { Doughnut } from 'vue-chartjs'
import { options } from '@/assets/testData/chartOptions'
import { sdgData } from '@/assets/testData/sdgTestData'
ChartJS.register(ArcElement, Tooltip, Legend)

export default {
  name: 'quizResultsView',
  inject: ['sessionService', 'quizResultService'],
  components: {
    SdgCardComponent,
    Doughnut
  },
  computed: {
    isAuthenticated () {
      return this.sessionService.isAuthenticated()
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
      top7: []
    }
  },
  /**
   * This created hook sets up all the data by linking the question scores given by the URL to the right SDG .
   * Sorting them and filling in the appropriate variables which are later passed as props to child components.
   * If there is no data or the url is invalid it redirects back to the quiz.
   * @author Romello ten Broeke
   */
  created () {
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
        console.log(this.data)
        // Fill in sdgData to pass as a prop later
        for (let i = 0; i < this.top7.length; i++) {
          const newTitle = names[i]
          // If the user is logged in this should change the action to a not general one. Will be implemented at a later point
          const newContribution = sdgData.find(sdg => sdg.id === this.top7[i].SDG).generalContribution
          const newDescription = sdgData.find(sdg => sdg.id === this.top7[i].SDG).description
          const newSrc = sdgData.find(sdg => sdg.id === this.top7[i].SDG).src
          const newGifSrc = sdgData.find(sdg => sdg.id === this.top7[i].SDG).gifSrc
          this.sdgData.push({
            title: newTitle,
            generalContribution: newContribution,
            description: newDescription,
            src: newSrc,
            gifSrc: newGifSrc
          })
        }
      //  Restarts the quiz if the URL cannot be parsed
      } catch (error) {
        this.$router.push('/quiz')
      }
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
</style>
