<template>
  <div>
    <h1 class="fs-1 fs-sd-2"> Would you like to do a regular or Sector Specific Quiz?</h1>
    <div class="container quizUnderTextSection">
      <div class=" justify-content-between answerBox m-auto row">
        <div class="col-md-6 col-12 quizButtonSection">
          <button @click="clickedYesButton(true)" type="button"
            class="btn btn-primary my-5 quizAnswerButton quizYesNoButton  "> Regular </button>
        </div>
        <div class="col-md-6 col-12 quizButtonSection">
          <button @click="clickedNoButton(false)" type="button"
            class="btn btn-primary my-5 quizAnswerButton quizYesNoButton  "> Sector Specific</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: 'QuizRegularOrSector',
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
        this.$emit('questionAnswered', givenAnswer)
      } catch (error) {
        console.log(error)
      }
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
