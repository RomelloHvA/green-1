<template>
    <div>
        <figure v-if="questionObject.imgPath !== null" class="quizImgSpot">
          <img :src="questionObject.imgPath" class="imgFit" alt="Image">
        </figure>
        <h1 class="fs-1 fs-sd-2">{{ questionObject.question }}</h1>
        <h5 v-show="isAnswerLimitBiggerThenOne">&#40;You can select {{ questionObject.answerLimit }} answers&#41;</h5>
        <div class="container">
            <div class="m-auto row " v-for="option in questionObject.options" :key="option.option">
                <div class="col-12 quizButtonSection">
                    <button @click="handleOptionClicked(option)" type="button" :class="{selectedButton: option.isSelected, quizAnswerButton: !option.isSelected}" class="btn my-5 quizMultipleChoiceButton" > {{ option.option }}</button>
                </div>
            </div>
            <div v-if="isAnswerLimitBiggerThenOne"  class="d-flex justify-content-end"  >
              <div class="nextButtonDiv" @mouseenter="nextButtonHover = true" @mouseleave="nextButtonHover = false">
              <button id="nextButton" @click="handleQuestionAnswered()" type="button" class="btn btn-primary my-5 quizNextButton d-inline-block" :disabled="!nextIsButtonEnabled"  aria-describedby="tooltip"
             >Next</button>
              </div>
            </div>
            <div id="tooltip" role="tooltip"  :class="{hidden: !showTooltip}">
              Select an answer
              <div id="arrow" data-popper-arrow :class="{hidden: !showTooltip}"></div>
            </div>
        </div>
    </div>
</template>

<script>
import { createPopper } from '@popperjs/core'

export default {
  name: 'QuizQuestionYesNoComponent',
  props: {
    questionObject: {
      type: Object,
      required: true
    }
  },
  data () {
    return {
      nextButtonHover: false
    }
  },
  methods: {
    /**
     * This is a function that will be called when the user clicks on the next button and emits the answer to the parent component
     * The answers are filtered to only the selected answers and then emitted to the parent component
     * @author Marco de Boer
     */
    async handleQuestionAnswered () {
      try {
        await this.questionObject.setGivenAnswers()
        this.$emit('questionAnswered')
      } catch (error) {
        console.log(error)
      }
    },
    /**
     * This is a function that will be called when the user clicks on an option and will toggle the selected value of the option
     * If the answer limit is 1 then the question will be answered immediately after the option is clicked
     * @param {MultipleChoiceOption} option
     * @author Marco de Boer
     */
    async handleOptionClicked (option) {
      if (this.questionObject.answerLimit === 1) {
        await option.toggleSelected().then(() => {
          this.handleQuestionAnswered()
        })
      } else if (option.isSelected || this.questionObject.answerLimit > await this.questionObject.getSelectedAnswersAmount()) {
        await option.toggleSelected()
      }
    }
  },
  computed: {
    selectedAnswersList () {
      return this.quizAnswers.filter(answer => answer.selected)
    },
    showTooltip () {
      return !this.nextIsButtonEnabled && this.nextButtonHover
    },
    nextIsButtonEnabled () {
      return this.questionObject.options.some(option => option.isSelected)
    },
    isAnswerLimitBiggerThenOne () {
      return this.questionObject.answerLimit > 1
    }
  },
  /**
   * This is a lifecycle hook that will be called when the component is created and setup all the values for tooltip
   * @author Marco de Boer
   */
  mounted () {
    const buttonForPopper = document.querySelector('#nextButton')
    const tooltipForPopper = document.querySelector('#tooltip')
    createPopper(buttonForPopper, tooltipForPopper, {
      placement: 'bottom',
      modifiers: [
        {
          name: 'offset',
          options: {
            offset: [0, 8]
          }
        }
      ]
    })
    document.querySelector('nav').scrollIntoView({ behavior: 'smooth' })
  }
}
</script>

<style>
.quizMultipleChoiceButton {
    font-size: 1.5rem;
    width: 80%;
    margin: 20px !important
}

.quizNextButton {
    font-size: 1.5rem;
    width: 200px;
}
.quizNextButton:disabled {
  background-color: #8664d696 !important;
  border: 2px solid #8664d696 !important;
}

.quizNextButton:hover {
    color: #fff !important;
    background-color: rgb(37, 61, 244) !important;
    border: 2px solid rgb(37, 61, 244) !important;
}

#tooltip {
  display: inline-block;
  background: #411C97;
  color: white;
  font-weight: bold;
  padding: 5px 10px;
  font-size: 18px;
  font-weight: 500;
  border-radius: 4px;
}
.nextButtonDiv{
  width: fit-content;
  height: fit-content;
}

.hidden {
  visibility: hidden !important;
}

#arrow {
  position: absolute;
  width: 8px;
  height: 8px;
  background: transparent;  /* No background here */
  visibility: visible;
}

#arrow::before {
  content: '';
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: #411C97;
  transform: rotate(45deg);
}

#tooltip[data-popper-placement^='top'] > #arrow {
  bottom: -4px;
}

#tooltip[data-popper-placement^='bottom'] > #arrow {
  top: -4px;
}

#tooltip[data-popper-placement^='left'] > #arrow {
  right: -4px;
}

#tooltip[data-popper-placement^='right'] > #arrow {
  left: -4px;
}
</style>
