<template>
  <div class="d-flex row" v-if="questionClone">
      <label class="justify-content-start h5" :for="questionClone.question">Question:</label>
      <textarea class="question-text" placeholder="Write a question" v-model="questionClone.question"
        :class="{ 'red-border': questionClone.questionIsEmpty }"></textarea>
      <div class="col-9 quizButtonSection p-0 justify-content-center m-auto mx-0">
        <label class="justify-content-start h5 m-auto col" :for="questionClone.answerLimit">Amount of options can
          select:</label>
        <input class="col mx-2 answerLimit" v-model="questionClone.answerLimit"
          :class="{ 'red-border': !questionClone.answerLimitIsValid }" />
      </div>
      <div class="m-auto row optionListBuilder" v-for="(option) in questionClone.options" :key="option.id">
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
            <DeleteButtonComponent :disabled="pendingBusy" @delete="deleteOption(option)" />
          </div>
        </div>
      </div>
      <div>
        <button class="btn btn-primary w-75" @click="addOptionToList">Add Option</button>
      </div>
    <div class="col-2">
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onBeforeMount, defineProps, defineExpose } from 'vue'
import MultipleChoiceOption from '@/models/MultipleChoiceOption'
import MultipleChoiceQuestio from '@/models/MultipleChoiceQuestion'
import DeleteButtonComponent from '@/components/buttons/DeleteButtonComponent'

/**
 * This component is used to build a multiple choice question.
 * It is used in the QuizBuilder component.
 * @author Marco de Boer
 */
const props = defineProps({
  question: {
    type: MultipleChoiceQuestio,
    required: true
  },
  pendingBusy: {
    type: Boolean,
    required: true
  }
})

const questionClone = ref(props.question)
const sdgOptions = ref([])

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
function validateQuestion () {
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
  validateQuestion
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
