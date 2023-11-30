<template>
  <div>
    <h1 class="fs-1 fs-sd-2">{{ questionObject.question }}</h1>
    <div class="container quizUnderTextSection">
      <div class=" justify-content-between answerBox m-auto row">
        <div class="col-md-6 col-12 quizButtonSection">
          <button @click="clickedYesButton" type="button"
            :class="{ selectedButton: yesIsSelected, quizAnswerButton: !yesIsSelected }"
            class="btn btn-primary my-5 quizAnswerButton quizYesNoButton  ">Yes</button>
        </div>
        <div class="col-md-6 col-12 quizButtonSection">
          <button @click="clickedNoButton" type="button"
            :class="{ selectedButton: noIsSelected, quizAnswerButton: !noIsSelected }"
            class="btn btn-primary my-5 quizAnswerButton quizYesNoButton  ">No</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import QuizQuestionTrueFalse from '@/models/YesNoQuestion.js'

export default {
  name: 'QuizQuestionYesNoComponent',
  props: {
    questionObject: {
      type: QuizQuestionTrueFalse,
      required: true
    }
  },
  /**
   * This is a function that will be called when the component is created and setup all the values
   * @param {*} props The props that are passed to this component
   * @author Marco de Boer
   */
  data () {
    return {}
  },
  mounted () {
    document.querySelector('nav').scrollIntoView({ behavior: 'smooth' })
  },
  methods: {
    clickedYesButton () {
      this.handleQuestionAnswered(true)
    },
    clickedNoButton () {
      this.handleQuestionAnswered(false)
    },
    /**
     * This is a function that will be called when the user clicks on the next button and emits the answer to the parent component
     * @param {boolean} answers is either true or false depending on the answer given by the user
     * @author Marco de Boer
     */
    handleQuestionAnswered (givenAnswer) {
      try {
        this.questionObject.setGivenAnswer(givenAnswer)
        this.$emit('questionAnswered')
      } catch (error) {
        console.log(error)
      }
    }
  },
  computed: {
    yesIsSelected () {
      return this.questionObject.givenAnswer === true
    },
    noIsSelected () {
      return this.questionObject.givenAnswer === false
    }
  }
}
</script>

<style>
.quizUnderTextSection {
  margin-top: 50px;
  margin-bottom: 50px;
}

.questionHeader {
  font-size: 2.5rem;
  font-weight: 500;
  margin-bottom: 50px;
}

.answerBox {
  max-width: 500px !important;
}

.quizYesNoButton {
  font-size: 1.5rem;
  width: 200px;
  height: fit-content;
  margin-top: 30px !important;
  margin-bottom: 30px !important;
  margin-right: 40px;
  margin-left: 40px;
}</style>
