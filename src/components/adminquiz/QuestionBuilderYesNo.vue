<template>
  <div class="flexRow" v-if="questionClone">
    <div class="col-10">
      <label class="justify-content-start h5" :for="questionClone.question">Question:</label>
      <textarea class="question-text" placeholder="How many of the following SDG goals have you worked on?"
        v-model="questionClone.question" :class="{ 'red-border': questionClone.questionIsEmpty }"></textarea>
      <div class="justify-content-center m-auto">
        <div class="justify-content-between answerBox m-auto row">
          <div class="col-md-6 col-12 quizButtonSection">
            <button class="btn btn-primary my-5 quizAnswerButton quizYesNoButton" disabled>Yes</button>
          </div>
          <div class="col-md-6 col-12 quizButtonSection">
            <button class="btn btn-primary my-5 quizAnswerButton quizYesNoButton" disabled>No</button>
          </div>
        </div>
      </div>
    </div>
    <div class="col-2 my-5">
      <div class="sdgSelecter">
        SDG:
        <select v-model="questionClone.sdg" @change="handleSDGChange" class="dropdown"
          :class="{ 'red-border': questionClone.sdgIsEmpty }">
          <option v-for="(value, key) in sdgOptions" :key="key">{{ value }}</option>
        </select>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onBeforeMount, defineProps, defineExpose } from 'vue'
import YesNoQuestion from '@/models/YesNoQuestion.js'

/**
 * This component is used to build a true/false question for the quiz builder.
 * @author Marco de Boer
 */

const props = defineProps({
  question: {
    type: YesNoQuestion,
    required: true
  }
})

const questionClone = ref(props.question)
const sdgOptions = ref([])

// #todo SDG should be loaded from the database
onBeforeMount(async () => {
  for (let i = 1; i <= 17; i++) {
    sdgOptions.value.push(i)
  }
})

function validateQuestion () {
  questionClone.value.questionIsEmpty = questionClone.value.question === null || questionClone.value.question === ''
  questionClone.value.sdgIsEmpty = questionClone.value.sdg === null
  if (questionClone.value.questionIsEmpty || questionClone.value.sdgIsEmpty) {
    return false
  }
  return true
}

const handleSDGChange = (event) => {
  questionClone.value.sdg = parseInt(questionClone.value.sdg)
}

defineExpose({
  validateQuestion
})
</script>

<style>

.question-text:focus {
  outline: none;
}

.question-footer {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
}

.answer-format,
.sdg-select {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
  height: 370px;
  overflow-y: auto;
}

.sample-btn {
  padding: 10px 20px;
  border: 2px solid purple;
  border-radius: 4px;
  cursor: not-allowed;
}

</style>
