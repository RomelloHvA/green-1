<template>
    <div class="container-fluid text-center quizMain" @click="skipAnimation">
      <div v-if="!quizStarted && !quizEnded" class="quizButtonSection ">
        <h1 v-show="showItemSequence[0]" class="slide-in-animation headerText2 fs-1 fs-sd-2">Welcome to the SDG Quiz!</h1>
        <h3 v-show="showItemSequence[1]" class="slide-in-animation m-5 headerText3"> You will be asked questions about different topics </h3>
        <h3 v-show="showItemSequence[2]" class="slide-in-animation m-5 headerText3"> answer the questions and find out which SDG's are compatible with you!</h3>
        <button v-show="showItemSequence[3]" @click="startQuiz" type="button" class="btn btn-primary my-5 startQuizButton slide-in-animation" :disabled="quiz === null">Start quiz</button>
        <h5 v-if="quiz === null && showItemSequence[3]" class="slide-in-animation text-danger">There currenlty seems to be no quiz available, please try again later</h5>
      </div>
       <!-- This is where the quiz progress bar will be displayed -->
      <!-- This is where the quiz questions will be displayed with the answers -->
      <div v-else-if="quizStarted && !quizEnded">
        <QuizProgressBarComponent :currentQuestionIndex="quizIndex + 1" :totalQuestions="quiz.totalQuestions"
        :totalQuestionsAnswered="totalQuestionsAnswered" v-on:changeQuestion="handleChangeQuestion"/>
        <div v-if="(isCurrentQuestionYesNo)">
          <QuizQuestionYesNoComponent :questionObject="currentQuestion" v-on:questionAnswered="handleQuestionAnswered"/>
        </div>
        <div v-else>
          <QuizQuestionMultipleChoiceComponent :questionObject="currentQuestion" v-on:questionAnswered="handleQuestionAnswered"/>
        </div>
      </div>
      <div v-else-if="!quizStarted && quizEnded" class="slide-in-animation">
        <h1 class="my-4 headerText2">Quiz ended!</h1>
        <div class="row  maxWidth800 m-auto justify-content-center my-5">
          <h2 class="col-6 headerText3">Calculating results...</h2>
          <div class="spinner-border spinnerColor " role="status">
            <span class="sr-only"></span>
          </div>
        </div>
      </div>
    </div>
</template>

<script>
import QuizQuestionYesNoComponent from '@/components/quiz/QuizQuestionYesNoComponent.vue'
import QuizQuestionMultipleChoiceComponent from '@/components/quiz/QuizQuestionMultipleChoiceComponent.vue'
import QuizProgressBarComponent from './QuizProgressBarComponent.vue'
import QuizQuestionTrueFalse from '@/models/YesNoQuestion.js'
import { onMounted, computed, watchEffect, onBeforeUnmount, ref, inject, onBeforeMount } from 'vue'
import { useRoute, onBeforeRouteLeave } from 'vue-router'
import router from '@/router'

export default {
  name: 'QuizComponent',
  components: {
    QuizQuestionYesNoComponent,
    QuizQuestionMultipleChoiceComponent,
    QuizProgressBarComponent
  },
  setup () {
    const quizStarted = ref(false)
    const quizEnded = ref(false)
    const quizIndex = ref(0)
    const quiz = ref(null)
    const totalQuestionsAnswered = ref(0)
    const currentQuestion = ref(null)
    const textIndex = ref(0)
    const characterIndex = ref(0)
    const showItemSequence = ref([false, false, false, false])
    const questionAnswered = ref(null)
    const quizLiveService = inject('quizLiveService')
    const route = useRoute()
    const sectorID = 1

    onBeforeMount(async () => {
      const results = await quizLiveService.asyncCustom('live', 'GET', null, { sectorID: sectorID })

      watchEffect(() => {
        quiz.value = results.entity.value
      })

      results.load()
    })
    /**
     * This is a function that will be called every 700ms and will show the next item in the showItemSequence array
     * This is used to show the welcome text in a sequence
     * @author Marco de Boer
     */
    onMounted(() => {
      window.addEventListener('beforeunload', beforeWindowUnload)
      setInterval(() => {
        if (showItemSequence.value.length === textIndex.value) {
          clearInterval()
          return
        }
        showItemSequence.value[textIndex.value] = true
        textIndex.value++
      }, 500)
    })

    /**
     * This function will be called when the user tries to leave the quiz page when they have not finished the quiz
     * @param {*} to you dont have to give these parameters, they are given by vue router
     * @param {*} from you dont have to give these parameters, they are given by vue router
     * @param {*} next you dont have to give these parameters, they are given by vue router
     * @author Marco de Boer
     */

    onBeforeRouteLeave((to, from, next) => {
      if (quizStarted.value && totalQuestionsAnswered.value !== 0) {
        if (window.confirm('You have not finished the quiz, your progress will be lost')) {
          totalQuestionsAnswered.value = 0
          next()
        } else {
          next(false)
        }
      }
      next()
    })

    onBeforeUnmount(() => {
      window.removeEventListener('beforeunload', beforeWindowUnload)
    })

    /**
     *  This method is called when the user tries to leave the page. It will ask the user if they want to discard the changes.
     * @param {*} e event you dont have to give this parameter, it will be given automatically
     * @autor Marco de Boer
     */
    function beforeWindowUnload (e) {
      if (!quizStarted.value && totalQuestionsAnswered.value === 0) {
        return
      }
      e.preventDefault()
      e.returnValue = 'You have not finished the quiz, your progress will be lost.'
    }

    watchEffect(() => {
      if (!quiz.value) return
      quizIndex.value = quiz.value.currentQuestionIndex
      questionAnswered.value = quiz.value.totalAnsweredQuestions
    })
    /**
     * This will be called when the quizIndexChanges and displays the appropriate question in the quizQuestionsObjectArray
     * @author Marco de Boer
     */

    /**
     * This function will start the quiz and set the quizStarted to true and the quizEnded to false
     * it will also create a new quiz object and set the currentQuestion to the first question in the quizQuestionsObjectArray
     * @author Marco de Boer
     */
    const startQuiz = async () => {
      try {
        currentQuestion.value = await quiz.value.getCurrentQuestion()
        quizStarted.value = true
        quizEnded.value = false
      } catch (error) {
        console.error(error)
      }
    }

    /**
     * This function will handle the question answered event from the QuizQuestionYesNoComponent and QuizQuestionMultipleChoiceComponent
     * It will check if the quiz is ended and if it is it will set the quizEnded to true and the quizStarted to false
     * It will also get the next question from the quiz and set the currentQuestion to the next question
     * if the quiz ended it will calculate the results
     * @author Marco de Boer
     */
    const handleQuestionAnswered = async () => {
      try {
        if (quizIndex.value + 1 === quiz.value.totalQuestions) {
          quizStarted.value = false
          quizEnded.value = true
        }
        currentQuestion.value = await quiz.value.getNextQuestion()
        totalQuestionsAnswered.value = await quiz.value.getTotalAnsweredQuestions()
        if (quizEnded.value) {
          const quizanswers = await quiz.value.setQuizResultObjectArray()
          router.push({
            path: `${route.matched[0].path}/results`,
            query: { quizanswers: encodeURIComponent(JSON.stringify(quizanswers)) }
          })
        }
      } catch (error) {
        console.error(error)
      }
    }

    const skipAnimation = () => {
      showItemSequence.value = [true, true, true, true]
    }

    /**
     * This function will handle the change question event from the QuizProgressBarComponent
     * @param {Number} change is either 1 or -1 to change to next or previous question
     * @author Marco de Boer
     */
    const handleChangeQuestion = async (change) => {
      try {
        if (change === 1) {
          currentQuestion.value = await quiz.value.getNextQuestion()
        } else {
          currentQuestion.value = await quiz.value.getPreviousQuestion()
        }
      } catch (error) {
        console.error(error)
      }
    }

    const isCurrentQuestionYesNo = computed(() => { return currentQuestion.value instanceof QuizQuestionTrueFalse })

    return {
      startQuiz, handleQuestionAnswered, skipAnimation, handleChangeQuestion, isCurrentQuestionYesNo, quizStarted, quizEnded, quizIndex, quiz, totalQuestionsAnswered, currentQuestion, showItemSequence, textIndex, characterIndex
    }
  }
}
</script>

<style>
.headerText3 {
  font-weight: 500;
  margin-bottom: 0;
  color: #292b2e;
}
.slide-in-form {
  transform: translateY(100%); /* Push the form off the bottom of the screen/container */
  opacity: 0;
  transition: all 0.5s ease-in-out;
}
.quizMain {
  padding-top: 30px;
  padding-bottom: 40px;
  min-height: 100vh;
  height: fit-content;
}

.quizButtonSection .quizAnswerButton {
  color: #6D3FD9 !important;
  background-color: transparent !important;
  border: 2px solid #6D3FD9 !important;
}

.quizButtonSection .selectedButton {
    color: #fff !important;
    background-color: #6D3FD9 !important;
    border: 2px solid #6D3FD9 !important;
}

.quizButtonSection .selectedButton:hover {
  background-color: #411C97 !important;
  border: 2px solid #411C97 !important;
}

.quizAnswerButton:hover {
  color: #fff !important;
  background-color: #7f5ad4e8 !important;
  border: 2px solid #7f5ad4e8 !important;
}

.startQuizButton {
  font-size: 1.5rem;
  width: 200px;
}
.spinnerColor {
  color: #6D3FD9;
}
.maxWidth800 {
  max-width: 800px;
}

.ripple {
  background-position: center;
  transition: background 0.5s;
}
.ripple:hover {
  background: #A38EE1 radial-gradient(circle, transparent 1%, #b0a0e0 1%) center/15000%;
}
.ripple:active {
  background-color: #b0a0e0;
  background-size: 100%;
  transition: background 0s;
}
.slide-in-animation {
  /* Initial position -50px off the top of its original position */
  transform: translateY(50px);
  opacity: 0; /* Initial opacity set to 0 to make it invisible */

  /* Animation configuration */
  animation: slideIn 0.8s ease-out forwards; /* Name, duration, easing function, fill mode */
}
.fade-exit-active {
  opacity: 0;
  transition: opacity 500ms ease-in-out;
}

@keyframes slideIn {
  to {
    transform: translateY(0); /* Target position */
    opacity: 1; /* Make it fully visible */
  }
}
</style>
