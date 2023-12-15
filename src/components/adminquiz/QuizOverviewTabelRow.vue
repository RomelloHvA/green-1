<template>
    <th class="selectorData sectorName">{{ sector.name }}</th>
    <td class="selectorData">
        <div class="flexRow justify-content-center">
        <div class="">
            <select v-model="selectedQuizForBuilder" class="form-select quizSelect">
            <option value="" selected disabled>Select a quiz</option>
            <option v-for="quiz in sector.quizzes" :key="quiz.id" :value="quiz"> {{ quiz.name}}</option>
            </select>
        </div>
        <div class="">
            <button @click="gotoQuizBuilder" class="btn btn-primary" :disabled="!selectedQuizForBuilder">
                Edit quiz
            </button>
        </div>
        </div>
    </td>
    <td class="selectorData">
        <div class="flexRow justify-content-center">
            <div class="">
                <select v-model="selectedQuizForLive" class="form-select quizSelect">
                    <option value="" selected disabled>Select a quiz</option>
                    <option value="None" class="text-danger">None</option>
                    <option v-for="quiz in publishedQuizzes" :key="quiz.id" :value="quiz"> {{ quiz.name}}</option>
                </select>
            </div>
            <div class="">
                <button @click="setQuizLive" class="btn btn-primary" :disabled="!selectedQuizForLive || setLiveIsPending">
                    <div class="d-flex row">
                        <div class="col">Set Live</div>
                        <div v-if="setLiveIsPending" class="col spinnerInButton p-0">
                            <div class="spinner-border text-light spinnerInButton" role="status">
                                <span class="sr-only"></span>
                            </div>
                        </div>
                    </div>
                </button>
            </div>
        </div>
    </td>
    <td class="selectorData liveQuizText">
        <div v-if="liveQuiz !== null && liveQuiz !== undefined">
            {{ liveQuiz.name }}
        </div>
        <div v-else class="text-danger">
            no quiz live
        </div>
    </td>
</template>

<script>
import Sector from '@/models/Sector'
import { ref, computed, onBeforeMount, inject, watchEffect } from 'vue'
import router from '@/router'
import { useToast } from 'vue-toast-notification'

export default {
  name: 'QuizOverviewTabelRow',
  props: {
    sector: {
      type: Sector,
      required: true
    },
    quizzes: {
      type: Array,
      required: true
    }
  },
  setup (props, { emit }) {
    const selectedQuizForLive = ref(null)
    const selectedQuizForBuilder = ref(null)
    const setLiveIsPending = ref(false)
    const setLiveError = ref(null)
    const quizService = inject('quizService')
    const liveQuiz = ref(null)
    const $toast = useToast()

    /**
     * In props all quizzes are given
     * This function sets the quizzes of the sector
     * After that it sets the live quiz for this sector
     */

    onBeforeMount(async () => {
      await props.sector.setQuizzes(props.quizzes)
      liveQuiz.value = await getLiveQuiz()
    })

    /**
     * This function sets the live quiz for this sector to display it in the table
     * @Author Marco de Boer
     */
    async function getLiveQuiz () {
      return props.sector.quizzes.find(quiz => quiz.isLive)
    }

    /**
     * This function sets the selected quiz for live
     * @Author Marco de Boer
     */
    const setQuizLive = async () => {
      if (selectedQuizForLive.value === 'None' && liveQuiz.value === undefined) {
        selectedQuizForLive.value = null
        return
      }
      if (selectedQuizForLive.value === 'None' && liveQuiz.value !== null) {
        liveQuiz.value.isLive = false
        selectedQuizForLive.value = liveQuiz.value
      } else {
        selectedQuizForLive.value.isLive = true
      }
      const results = await quizService.asyncSave(selectedQuizForLive.value, null, 'PUT')

      watchEffect(() => {
        setLiveIsPending.value = results.isPending.value
        setLiveError.value = results.error.value
      })

      results.load().then(() => {
        if (setLiveError.value === null) {
          if (!selectedQuizForLive.value.isLive) {
            liveQuiz.value = null
          } else {
            liveQuiz.value = selectedQuizForLive.value
          }
          selectedQuizForLive.value = null
        } else if (setLiveError.value !== null) {
          $toast.error(setLiveError.value.message)
        }
      })
    }

    /**
     * This function navigates to the quiz builder with the selected quiz
     * @Author Marco de Boer
     */
    const gotoQuizBuilder = () => {
      router.push({ name: 'QuizBuilder', params: { id: selectedQuizForBuilder.value.id } })
    }

    const publishedQuizzes = computed(() => { return props.sector.quizzes.filter(quiz => quiz.isPublished) })

    const isLiveQuiz = computed(() => { return props.sector.quizzes.filter(quiz => quiz.isLive) })

    return { publishedQuizzes, selectedQuizForLive, isLiveQuiz, setQuizLive, selectedQuizForBuilder, setLiveIsPending, setLiveError, liveQuiz, gotoQuizBuilder }
  }
}
</script>

<style>
    .selectorData {
        max-width: 100px;
        width: 200px;
    }

    .liveQuizText {
        max-width: 100px;
        width: 200px;
        max-height: 20px;
    }

    .quizSelect {
        max-width: 250px;
        min-width: 250px;
        margin-right: 20px;
    }

    .sectorName {
      text-align: center;
      margin: auto;
      font-size: 1.1rem;
      font-weight: 550;
    }
</style>
