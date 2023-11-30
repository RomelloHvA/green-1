<template>
    <div class="container">
      <div v-if="error">
        <ErrorComponent :error="error"/>
        <button class="btn btn-primary col-1" @click="backToOverview" :class="{ 'disabled' : hasChanged || pendingBusy}">
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
              </svg>
        </button>
      </div>
      <div v-else-if="isPending">
        <LoadingComponent/>
      </div>
      <div v-else>
        <section v-if="quiz != null">
          <div class="row">
            <button class="btn btn-primary col-1" @click="backToOverview" :class="{ 'disabled' : hasChanged || pendingBusy}">
              <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-box-arrow-left" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M6 12.5a.5.5 0 0 0 .5.5h8a.5.5 0 0 0 .5-.5v-9a.5.5 0 0 0-.5-.5h-8a.5.5 0 0 0-.5.5v2a.5.5 0 0 1-1 0v-2A1.5 1.5 0 0 1 6.5 2h8A1.5 1.5 0 0 1 16 3.5v9a1.5 1.5 0 0 1-1.5 1.5h-8A1.5 1.5 0 0 1 5 12.5v-2a.5.5 0 0 1 1 0v2z"/>
                <path fill-rule="evenodd" d="M.146 8.354a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L1.707 7.5H10.5a.5.5 0 0 1 0 1H1.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3z"/>
              </svg>
            </button>
            <h1>QuizBuilder</h1>
          </div>
          <div class="container d-flex justify-content-center">
              <div class="flexRow m-auto my-4 w-75 justify-content-between" justify-content-between>
                  <div class="flexRow text-start m-auto">
                    <label for="quizName" class="quizBuilderLabel text-start">Quiz Name:</label>
                    <input v-model="quiz.quizName" type="text" autocomplete="off" class="form-control quizNameInput" id="quizName">
                  </div>
                  <div>
                    <SectorDropDownComponent :sector="quiz.sector" @sectorSelected="setSector"/>
                  </div>
                  <div class="justify-content-center m-auto">
                    <button type="button" :class="{ 'disabled' : !hasChanged || pendingBusy}" @click="saveQuiz()" class="btn btn-success m-1">
                      <div class="d-flex row">
                          <div class="col">
                          {{saveButtonText}}
                          </div>
                          <div v-if="saveQuizIsPending" class="col spinnerInButton p-0">
                              <div class="spinner-border text-light spinnerInButton" role="status">
                                  <span class="sr-only"></span>
                              </div>
                          </div>
                      </div>
                    </button>
                  </div>
                  <div class="justify-content-center m-auto">
                    <button class="questionDeleteButton m-auto" @mouseenter="deleteButtonHover = true" @mouseleave="deleteButtonHover = false" @click="deleteQuiz">
                    <svg v-if="!deleteButtonHover" xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="red" class="bi bi-trash" viewBox="0 0 20 20">
                        <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z"/>
                        <path d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z"/>
                    </svg>
                    <svg v-if="deleteButtonHover"  xmlns="http://www.w3.org/2000/svg" width="35" height="35" fill="red" class="bi bi-trash-fill" viewBox="0 0 20 20">
                        <path d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z"/>
                    </svg>
                    </button>
                  </div>
                  <div class="col-2">
                    <div class="flexRow justify-content-between">
                      <label class="radioButtonLabel" for="statusConcept">Concept</label>
                      <input v-model="selectedValue" class="radioButton" type="radio" id="statusConcept" name="status" value="Concept" >
                    </div>
                    <div class="flexRow justify-content-between">
                      <label class="radioButtonLabel" for="statusPublished">Publishable</label>
                      <input v-model="selectedValue" class="radioButton" type="radio" id="statusPublished" name="status" value="Published">
                    </div>
                  </div>
              </div>
          </div>
          <div v-for="(value, key) in quiz.quizQuestions" :key="key">
                  <QuizBuilderTrueFalse class="my-2" v-if="isTrueFalseQuesetion(value)" :question="value" @deleteQuestion="deleteQuestion" @saveQuestion="saveQuestion" @moveQuestion="moveQuestion"/>
                  <QuizBuilderMultipleChoice class="my-2" v-else-if="isMultipleChoiceQuestion(value)" :question="value" @deleteQuestion="deleteQuestion" @saveQuestion="saveQuestion" @moveQuestion="moveQuestion"/>
          </div>
          <div class="d-flex justify-content-center flexRow">
              <div class="quizBuilderQuestionType">
                  <select class="form-select " aria-label="Select a type of question" v-model="selectedQuestionType" >
                      <option value="" selected disabled>Select a type of question</option>
                      <!-- #todo autoload from available options -->
                      <option v-for="(value,key) in questionTypes" :key="key"> {{ value }}</option>
                  </select>
              </div>
              <div class="">
                  <button class="col btn btn-primary" @click="addQuestion(selectedQuestionType)">Add Question</button>
              </div>
          </div>
        </section>
      </div>
    </div>
</template>
<script>
import { ref, inject, onBeforeMount, watchEffect, computed, watch } from 'vue'
import { useRoute } from 'vue-router'
import QuizBuilderTrueFalse from './QuizBuilderTrueFalse.vue'
import YesNoQuestion from '@/models/YesNoQuestion'
import QuizBuilderMultipleChoice from './QuizBuilderMultipleChoice.vue'
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import ErrorComponent from '@/components/ErrorComponent'
import LoadingComponent from '@/components/LoadingComponent'
import router from '@/router'
import { useToast } from 'vue-toast-notification'
import SectorDropDownComponent from './SectorDropDownComponent.vue'

export default {
  name: 'QuizBuilder',
  components: {
    QuizBuilderTrueFalse,
    ErrorComponent,
    LoadingComponent,
    QuizBuilderMultipleChoice,
    SectorDropDownComponent
  },
  setup (props, { emit }) {
    const quizService = inject('quizService')
    const questionTypes = ref(['Yes/No', 'MultipleChoice'])
    const selectedQuestionType = ref('')
    const saveButtonText = ref('Saved')
    const load = ref(null)
    const quiz = ref(null)
    const isPending = ref(false)
    const error = ref(null)
    const quizOriginal = ref(null)
    const saveQuizIsPending = ref(false)
    const saveQuizError = ref(null)
    const route = useRoute()
    const $toast = useToast()
    const deleteButtonHover = ref(false)

    const cloneQuiz = async (quizOriginal) => {
      quiz.value = await quizOriginal.clone()
    }

    onBeforeMount(async () => {
      const results = await quizService.asyncFindById(route.params.id)

      load.value = results.load

      watchEffect(() => {
        quizOriginal.value = results.entity.value
        isPending.value = results.isPending.value
        error.value = results.error.value
      })

      load.value().then(async () => {
        if (error.value === null) {
          await cloneQuiz(quizOriginal.value)
        }
        if (error.value === 'Could not fetch the data for that resource') {
          error.value = 'Could not find quiz with id: ' + route.params.id
        }
      })
    })

    function setIndexOrder () {
      for (let i = 0; i < quiz.value.quizQuestions.length; i++) {
        quiz.value.quizQuestions[i].index = i + 1
      }
      quiz.value.totalQuestions = quiz.value.quizQuestions.length
    }

    function validateValues () {
      let valid = true
      quiz.value.quizNameIsEmpty = quiz.value.quizName === null || quiz.value.quizName === ''
      if (quiz.value.quizNameIsEmpty) {
        valid = false
      }

      return valid
    }

    const moveQuestion = (index, newIndex) => {
      if (newIndex < 0 || newIndex === quiz.value.quizQuestions.length) {
        return
      }
      const question = quiz.value.quizQuestions[index]
      quiz.value.quizQuestions.splice(index, 1)
      quiz.value.quizQuestions.splice(newIndex, 0, question)
      setIndexOrder()
    }

    const addQuestion = async (questionType) => {
      if (questionType === 'Yes/No') {
        quiz.value.quizQuestions.push(
          await new YesNoQuestion(null, quiz.value.totalQuestions + 1, null, null)
        )
      } else if (questionType === 'MultipleChoice') {
        quiz.value.quizQuestions.push(
          await new MultipleChoiceQuestion(null, quiz.value.totalQuestions + 1, null, null)
        )
      }
      quiz.value.totalQuestions = quiz.value.quizQuestions.length
    }

    const deleteQuestion = (index) => {
      console.log(quiz.value.quizQuestions)
      quiz.value.quizQuestions.splice(index, 1)
      setIndexOrder()
      console.log(quiz.value.quizQuestions)
    }

    const saveQuiz = async () => {
      if (validateValues() === false) {
        return
      }

      const { isPending, error, load } = await quizService.asyncSave(quiz.value)

      watchEffect(() => {
        saveQuizIsPending.value = isPending.value
        saveQuizError.value = error.value
        if (saveQuizIsPending.value) {
          saveButtonText.value = 'Saving'
        }
      })

      load().then(() => {
        if (saveQuizError.value === null) {
          saveButtonText.value = 'Saved'
          quizOriginal.value = quiz.value
          emit('updateQuizzes')
        } else if (saveQuizError.value !== null) {
          $toast.error('Could not save quiz ' + quiz.value.quizName)
        }
      })
    }

    const saveQuestion = (question) => {
      quiz.value.quizQuestions[question.index - 1] = question
    }

    const backToOverview = () => {
      router.push({ path: '/admin_dashboard/quiz' })
    }

    const setSector = (sector) => {
      quiz.value.sector = sector
    }

    const deleteQuiz = async () => {
      if (!window.confirm('Are you sure you want to delete this quiz?')) {
        return
      }
      const result = await quizService.asyncDeleteById(quiz.value.id)

      result.load().then(() => {
        console.log(result.error.value)
        if (result.error.value === null || result.error.value === 'Unexpected end of JSON input') {
          $toast.success('Quiz ' + quiz.value.quizName + ' deleted')
          emit('updateQuizzes')
          backToOverview()
        } else {
          $toast.error('Could not delete quiz ' + quiz.value.quizName)
        }
      })
    }

    const hasChanged = computed(() => { return quizOriginal.value !== null && !quizOriginal.value.equals(quiz.value) && quizOriginal.value.id !== null })

    const pendingBusy = computed(() => { return saveQuizIsPending.value })

    const selectedValue = computed({
      get () {
        return quiz.value.isConcept ? 'Concept' : 'Published'
      },
      set (value) {
        quiz.value.isConcept = value === 'Concept'
        quiz.value.isPublished = value === 'Published'
      }
    })

    watch(hasChanged, (newValue) => {
      if (newValue && quiz.value !== null) {
        saveButtonText.value = 'Save'
      } else {
        saveButtonText.value = 'Saved'
      }
    })

    return {
      questionTypes, addQuestion, selectedQuestionType, deleteQuestion, error, isPending, quiz, saveQuestion, moveQuestion, saveButtonText, hasChanged, pendingBusy, saveQuiz, saveQuizIsPending, selectedValue, backToOverview, deleteButtonHover, deleteQuiz, setSector
    }
  },
  methods: {
    isTrueFalseQuesetion (question) {
      return question instanceof YesNoQuestion
    },
    isMultipleChoiceQuestion (question) {
      return question instanceof MultipleChoiceQuestion
    }
  }
}
</script>
<style>
.quizBuilderLabel {
    font-size: 20px;
    font-weight: 600;
    margin-right: 10px;
}

.quizBuilderQuestionType {
    width: 250px;
    margin-right: 30px;
}

.flexRow {
    display: flex;
    flex-direction: row;
}

.quizNameInput {
    width: 300px;
}

template {
    min-height: 100vh;
}

.red-border {
  border: 1px solid red !important;
}

.radioButtonLabel {
    font-size: 20px;
    font-weight: 600;
    margin-right: 10px;
}

.radioButton {
    margin-right: 10px;
    color: #6D3FD9;
}
</style>
