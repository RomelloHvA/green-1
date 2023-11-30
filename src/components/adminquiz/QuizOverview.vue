<template>
  <div class="minvh100">
    <div v-if="error">
      <ErrorComponent :error="error" />
    </div>
    <div v-else-if="isPending">
      <LoadingComponent />
    </div>
    <div v-else>
      <section v-if="quizzes !== null">
        <div class="my-5 mw90 m-auto">
          <div v-if="!isBuilderRoute">
            <h1>Quiz Overview</h1>
            <div class="flexRow m-auto container mtop-50">
              <div class="d-flex align-top m-auto quizOverviewCardContent flexRow align-items-center gap-3">
                <div class="quizOverViewCardContentText">Edit quiz</div>
                <select v-model="selectedQuizForBuilder" class="form-select editSelecter">
                  <option value="" selected disabled>Select a quiz</option>
                  <!-- #todo autoload from available options -->
                  <option v-for="quiz in quizzes" :key="quiz.id" :value="quiz"> {{ quiz.quizName }}</option>
                </select>
                <button @click="gotoQuizBuilder" class="btn btn-primary" :disabled="!selectedQuizForBuilder">
                  Edit quiz
                </button>
              </div>
              <div class="d-flex align-top m-auto quizOverviewCardContent flexRow align-items-center gap-3">
                <div class="quizOverViewCardContentText">Create Quiz</div>
                <button @click="createQuiz" class="btn btn-primary" :disabled="createIsPending">
                  <div class="d-flex row">
                    <div class="col">Create quiz</div>
                    <div v-if="createIsPending" class="col spinnerInButton p-0">
                      <div class="spinner-border text-light spinnerInButton" role="status">
                        <span class="sr-only"></span>
                      </div>
                    </div>
                  </div>
                </button>
              </div>
            </div>
            <div class="table-responsive mtop-50" v-if="sectors">
              <table class="table table-ligth">
                <thead>
                  <tr class="sectorQuizTableLabels">
                    <th scope="col">Sector:</th>
                    <th scope="col">Quizzes</th>
                    <th scope="col">Ready for Live</th>
                    <th scope="col">Live</th>
                  </tr>
                </thead>
                <tbody>
                  <div v-if="sectorsError">
                    <ErrorComponent :error="sectorsError" />
                  </div>
                  <div v-else-if="sectorsIsPending">
                    <LoadingComponent />
                  </div>
                  <tr v-else v-for="sector in sectors" :key="sector.id">
                    <QuizOverviewTabelRow :sector="sector" :quizzes="quizzes"/>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <router-view @updateQuizzes="updateQuizzes" />
        </div>
      </section>
    </div>
  </div>
</template>

<script>
import { ref, inject, onBeforeMount, watchEffect, computed } from 'vue'
import { useRoute } from 'vue-router'
import ErrorComponent from '@/components/ErrorComponent'
import LoadingComponent from '@/components/LoadingComponent'
import router from '@/router'
import Quiz from '@/models/Quiz'
import { useToast } from 'vue-toast-notification'
import QuizOverviewTabelRow from './QuizOverviewTabelRow.vue'

export default {
  name: 'QuizOverview',
  components: {
    ErrorComponent,
    LoadingComponent,
    QuizOverviewTabelRow
  },
  setup () {
    const quizService = inject('quizService')
    const quizzes = ref([])
    const conceptQuizzes = ref([])
    const load = ref(null)
    const isPending = ref(false)
    const error = ref(null)
    const route = useRoute()
    const selectedQuizForBuilder = ref('')
    const createIsPending = ref(false)
    const createError = ref(null)
    const $toast = useToast()
    const sectorService = inject('sectorService')
    const sectors = ref([])
    const sectorsIsPending = ref(false)
    const sectorsError = ref(null)

    /**
     * This function loads all the quizzes and then filters them and fetch sectors
     * @Author Marco de Boer
     */
    onBeforeMount(async () => {
      const results = await quizService.asyncFindAll()

      load.value = results.load

      watchEffect(() => {
        quizzes.value = results.entities.value
        isPending.value = results.isPending.value
        error.value = results.error.value
      })

      load.value().then(async () => {
        if (error.value === null) {
          filterQuizzes()
          await fetchSectors()
        }
      })
    })

    async function fetchSectors () {
      const sectorResults = await sectorService.asyncFindAll()

      watchEffect(() => {
        sectors.value = sectorResults.entities.value
        sectorsIsPending.value = sectorResults.isPending.value
        sectorsError.value = sectorResults.error.value
      })

      sectorResults.load().then(() => {
        if (sectorResults.error.value) {
          console.log(sectorResults.error.value)
        }
      })
    }

    /**
     * This function filters the quizzes and only adds the concept quizzes to the conceptQuizzes array
     */
    function filterQuizzes () {
      // Clear the existing quizzes
      conceptQuizzes.value.splice(0, conceptQuizzes.value.length)

      quizzes.value.forEach(quiz => {
        if (quiz.isConcept) {
          conceptQuizzes.value.push(quiz)
        }
      })
    }

    /**
     * This function updates the quizzes after a change from builder
     * @Author Marco de Boer
     */
    const updateQuizzes = () => {
      load.value().then(() => {
      })
    }

    const gotoQuizBuilder = () => {
      router.push({ name: 'QuizBuilder', params: { id: selectedQuizForBuilder.value.id } })
    }

    /**
     * This function creates a new quiz
     * @Author Marco de Boer
     */
    const createQuiz = async () => {
      const result = await quizService.asyncSave(new Quiz({ id: 0 }))

      watchEffect(() => {
        createIsPending.value = result.isPending.value
        createError.value = result.error.value
      })

      result.load().then(() => {
        if (result.error.value) {
          $toast.error('Unable to create quiz, please try again later')
          return
        }
        updateQuizzes()
        router.push({ name: 'QuizBuilder', params: { id: result.entity.value.id } })
      })
    }

    const getConcept = computed((quizzes) => { return quizzes.filter(quiz => quiz.isConcept) })

    const isBuilderRoute = computed(() => { return route.path.includes('/quiz/builder') })

    return {
      quizzes, conceptQuizzes, isPending, error, isBuilderRoute, selectedQuizForBuilder, gotoQuizBuilder, updateQuizzes, createQuiz, createIsPending, sectors, getConcept, sectorsError, sectorsIsPending
    }
  }
}
</script>

<style>
.mtop-50 {
  margin-top: 50px !important;
}

.editSelecter {
  max-width: 200px;
}

.mw90 {
  max-width: 90%;
}

.quizBuilder .nav-item .nav-link {
  color: #1d1d1d !important;
  font-size: 20px;
  font-weight: 600;
}

.quizBuilder .nav-item:hover .nav-link:hover {
  color: #a2a2a2 !important;
}

.quizBuilder .active {
  border-bottom: 2px solid #000000;
}

.quizBuilder {
  list-style: none;
}

.liveQuizText {
  color: #411C97 !important;
  font-weight: 550;
  font-size: 1.1rem;
}

.quizCardTitles {
  font-weight: 600;
  font-size: 1.3rem;
}

.quizOverviewCards {
  width: 250px;
  height: 200px;
  flex-shrink: 0;
  background-color: #C5B2F0;
  border-radius: 10px;
  padding: 5px;
}

.quizOverviewCardContent {
  width: 100%;
  height: 100%;
  text-align: center;
  align-items: top;
  margin-top: 10px;
  justify-content: center;
  margin: auto;
  color: rgb(0, 0, 0) !important;
  font-weight: 500;
  font-size: 1.3rem;
}

.minvh100 {
  min-height: 100vh;
}

.sectorQuizTableLabels {
  font-weight: 600;
  font-size: 1.3rem;
}
</style>
