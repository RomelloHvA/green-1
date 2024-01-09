<template>
  <div>
    <div class="d-flex justify-content-center align-items-center">
      <h2 class="ml-custom" id="title">These are your top sdgs!</h2>

      <div class="paste-button">
        <button class="button">Sort by &nbsp; ▼</button>
        <div class="dropdown-content">
          <a class="dropdown-item" @click="sortResults('asc')" id="top">Date ascending</a>
          <a class="dropdown-item" @click="sortResults('desc')" id="bottom">Date descending</a>
        </div>
      </div>
      <button @click="onBack" class="btn-secondary goBackButton">Back to profile</button>
    </div>
    <div class="mt-2 mb-5 resultBody">
      <div class="row">
        <table>
          <tbody id="scroll-screen">
          <!-- This loop is used to get the users Quiz Results -->
          <tr class="container sdgGoals" style="margin-bottom: 40px" v-for="quizResult in sortedQuizResults" :key="quizResult.result_id">
            <div class="row">
              <td class="containeResult justify-content-center" style="font-weight: bold; color: #6D3FD9;">These are your top 3 SDGs from the Quiz you made on: {{ formatDate(quizResult.dateOfQuiz) }}</td>
              <!-- This loop gets all the data for the sdg cards -->
              <div v-for="sdgId in quizResult.sdgArray" :key="sdgId" class="col" style="width: 20vh">
                <sdg-card-component :sdg-data="getSdgDataById(sdgId)" />
              </div>
            </div>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import sdgCardComponent from '@/components/quizResultsComponents/sdgCardComponent'
import { sdgData } from '@/assets/testData/sdgTestData'

export default {
  name: 'ResultPage',
  components: { sdgCardComponent },
  inject: ['profileService', 'quizResultService', 'sessionService'],
  data () {
    return {
      account: sessionStorage.getItem('ACCOUNT'),
      quizResults: [],
      sdgData: sdgData,
      sortedQuizResults: [],
      sortDirection: 'desc'
    }
  },
  beforeMount () {
    this.$router.push({ path: '/results' })
  },
  async created () {
    this.getQuizResults()
    this.sortResults('desc')
  },
  methods: {
    formatDate (dateString) {
      const options = { year: 'numeric', month: 'numeric', day: 'numeric' }
      return new Date(dateString).toLocaleDateString(undefined, options)
    },
    onBack () {
      this.$router.push({ path: '/profile' })
    },
    async getQuizResults () {
      try {
        const currentUser = await this.sessionService.currentAccount.user_id
        this.quizResults = await this.quizResultService.getByUserId(currentUser)
        this.sortedQuizResults = [...this.quizResults]
      } catch (e) {
        console.error(e)
      }
    },
    getSdgDataById (sdgId) {
      return this.sdgData.find(sdg => sdg.id === sdgId)
    },
    sortResults (direction) {
      this.sortDirection = direction
      this.sortedQuizResults.sort((a, b) => {
        const dateA = new Date(a.dateOfQuiz)
        const dateB = new Date(b.dateOfQuiz)
        return direction === 'asc' ? dateA - dateB : dateB - dateA
      })
    }
  }
}
</script>

<style scoped>
.ml-custom {
  padding-top: 20px;
  margin-left: 65vh;
  margin-right: 27vh;
}

.dropdown-item{
  cursor: pointer;
}

.goBackButton {
  margin: 0.5rem;
  width: 10rem;
  border-radius: 20px;
  align-self: center;
}

.sdgGoals {
  width: 80vw;
  height: 40vw;
  display: flex;
  flex-direction: column;
  flex-wrap: wrap;
  justify-content: space-evenly;
  align-items: center;
  margin-top: 1rem;
  border: #6D3FD9 solid 1px;
  border-radius: 20px;
}

.paste-button {
  position: relative;
  display: block;
  font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
}

.button {
  background-color: #6D3FD9;
  color: white;
  padding: 10px 15px;
  font-size: 15px;
  font-weight: bold;
  border: 2px solid transparent;
  border-radius: 15px;
  cursor: pointer;
}

.dropdown-content {
  display: none;
  font-size: 13px;
  position: absolute;
  z-index: 1;
  min-width: 200px;
  background-color: #212121;
  border: 2px solid #6D3FD9;
  border-radius: 0px 15px 15px 15px;
  box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
}

.dropdown-content a {
  color: white;
  padding: 8px 10px;
  text-decoration: none;
  display: block;
  transition: 0.1s;
}

.dropdown-content a:hover {
  background-color: #6D3FD9;
  color: white;
}

.dropdown-content a:focus {
  background-color: #212121;
  color: red;
}

.dropdown-content #top:hover {
  border-radius: 0px 13px 0px 0px;
}

.dropdown-content #bottom:hover {
  border-radius: 0px 0px 13px 13px;
}

.paste-button:hover button {
  border-radius: 15px 15px 0px 0px;
}

.paste-button:hover .dropdown-content {
  display: block;
}

h6 {
  height: 1rem;
  font-weight: bold;
}
</style>
