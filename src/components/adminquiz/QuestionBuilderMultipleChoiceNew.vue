<template>
  <div class="d-flex row" v-if="questionClone">
    <div class="my-5">
      <label class="justify-content-start h5" :for="questionClone.question">Question:</label>
      <textarea class="question-text" placeholder="Write a question" v-model="questionClone.question"
        :class="{ 'red-border': questionClone.questionIsEmpty }"></textarea>
      <div class="col-9 quizButtonSection p-0 justify-content-center m-auto mx-0">
        <label class="justify-content-start h5 m-auto col" :for="questionClone.answerLimit">Amount of options can
          select:</label>
        <input class="col mx-2 answerLimit" v-model="questionClone.answerLimit"
          :class="{ 'red-border': !questionClone.answerLimitIsValid }" />
      </div>
      <div class="m-auto row optionListBuilder" v-for="(option, key) in questionClone.options" :key="option.id">
        <div class="m-auto row multipleChoiceBuilderQuestionButton">
          <div class="col-9 quizButtonSection p-0 justify-content-center m-auto mx-0">
            <label class="justify-content-start h5 m-auto" :for="option.option">Option:</label>
            <input class="multipleChoiceBuilderQuestionInput" v-model="option.option"
              :class="{ 'red-border': option.optionIsEmpty }" />
          </div>
          <div class="sdgSelecter col-2 d-flex row m-auto">
            <div class="h5 m-0">
              SDG:
            </div>
            <select v-model="option.sdg" @change="handleSDGChange(option)" class="dropdown col-10"
              :class="{ 'red-border': option.optionIsEmpty }">
              <option v-for="(value, key) in sdgOptions" :key="key">{{ value }}</option>
            </select>
          </div>
          <div class="col-1">
            <div class="margin31"></div>
            <button class="questionDeleteButton justify-content-center m-auto"
              @mouseenter="deleteButtonHoverOption[key] = true" @mouseleave="deleteButtonHoverOption[key] = false"
              @click="deleteOption(option)">
              <svg v-if="!deleteButtonHoverOption[key]" xmlns="http://www.w3.org/2000/svg" width="35" height="35"
                fill="red" class="bi bi-trash" viewBox="0 0 20 20">
                <path
                  d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm2.5 0a.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5Zm3 .5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6Z" />
                <path
                  d="M14.5 3a1 1 0 0 1-1 1H13v9a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V4h-.5a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1H6a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1h3.5a1 1 0 0 1 1 1v1ZM4.118 4 4 4.059V13a1 1 0 0 0 1 1h6a1 1 0 0 0 1-1V4.059L11.882 4H4.118ZM2.5 3h11V2h-11v1Z" />
              </svg>
              <svg v-if="deleteButtonHoverOption[key]" xmlns="http://www.w3.org/2000/svg" width="35" height="35"
                fill="red" class="bi bi-trash-fill" viewBox="0 0 20 20">
                <path
                  d="M2.5 1a1 1 0 0 0-1 1v1a1 1 0 0 0 1 1H3v9a2 2 0 0 0 2 2h6a2 2 0 0 0 2-2V4h.5a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H10a1 1 0 0 0-1-1H7a1 1 0 0 0-1 1H2.5zm3 4a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 .5-.5zM8 5a.5.5 0 0 1 .5.5v7a.5.5 0 0 1-1 0v-7A.5.5 0 0 1 8 5zm3 .5v7a.5.5 0 0 1-1 0v-7a.5.5 0 0 1 1 0z" />
              </svg>
            </button>
          </div>
        </div>
      </div>
      <div>
        <button class="btn btn-primary w-75" @click="addOptionToList">Add Option</button>
      </div>
    </div>
    <div class="col-2">
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onBeforeMount, defineProps, defineExpose } from 'vue'
import MultipleChoiceOption from '@/models/MultipleChoiceOption'
import MultipleChoiceQuestio from '@/models/MultipleChoiceQuestion'

/**
 * This component is used to build a multiple choice question.
 * It is used in the QuizBuilder component.
 * @author Marco de Boer
 */
const props = defineProps({
  question: {
    type: MultipleChoiceQuestio,
    required: true
  }
})

const questionClone = ref(props.question)
const sdgOptions = ref([])
const deleteButtonHoverOption = ref([])

// #todo load in all SDG's from database
onBeforeMount(async () => {
  for (let i = 1; i <= 17; i++) {
    sdgOptions.value.push(i)
  }
})

/**
 * This function validates the options of the question.
 * It will also set the optionIsEmpty and sdgIsEmpty properties of the options.
 * @returns {boolean} Returns true if the options are valid, false if not.
 */
function validateOptions () {
  let isValid = true
  questionClone.value.options.forEach(option => {
    option.optionIsEmpty = !option.option
    option.sdgIsEmpty = option.sdg === null
    if (option.optionIsEmpty || option.sdgIsEmpty) {
      isValid = false
    }
  })
  if (questionClone.value.question === null || questionClone.value.question === '') {
    questionClone.value.questionIsEmpty = true
    isValid = false
  } else {
    questionClone.value.questionIsEmpty = false
  }

  questionClone.value.answerLimitIsValid = answerLimitIsValid.value
  if (answerLimitIsValid.value === false) {
    isValid = false
  }

  return isValid
}

const deleteOption = (option) => {
  questionClone.value.options.splice(questionClone.value.options.indexOf(option), 1)
}

const handleSDGChange = (option) => {
  option.sdg = parseInt(option.sdg)
}

const addOptionToList = () => {
  questionClone.value.options.push(new MultipleChoiceOption())
}

const answerLimitIsValid = computed(() => { return questionClone.value.answerLimit >= 1 && questionClone.value.answerLimit <= questionClone.value.options.length })

defineExpose({
  questionClone: questionClone,
  validateOptions: validateOptions
})
</script>

<style>
.multipleChoiceBuilderQuestionButton {
  margin: 10px;
  width: 90%;
  padding: 0;
  border-radius: 5px;
  padding: 5px;
}

.multipleChoiceBuilderQuestionInput {
  height: 40px;
  border-radius: 8px;
  width: 100%;
  border: 1px grey solid;
}

.optionListBuilder {
  margin-top: 20px !important;
  margin-bottom: 20px !important;
}

.margin31 {
  margin-top: 31px;
}

.answerLimit {
  width: 70px;
}</style>
