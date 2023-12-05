<template>
  <div v-if="questionClone" class="question-card justify-content-center m-auto ">
    <div class="question-header">
      <div class="d-flex gap-1">
        <span class="question-number">{{ questionClone.index }}</span>
        <button class="btn btn-primary questionArrowButton" @click="moveQuestionDown">&#8595;</button>
        <button class="btn btn-primary questionArrowButton" @click="moveQuestionUp">&#8593;</button>
      </div>
      <div>
        <SaveButtonComponent :disabled=" !hasChanged || pendingBusy" :isPending="saveQuestionIsPending"
          @save="saveQuestion" />
        <DeleteButtonComponent :disabled="pendingBusy" @delete="deleteQuestion" />
      </div>
    </div>
    <div class="d-flexRow">
        <UploadImageComponent class="uploadImageSpace" ref="uploadImage" @image-previewed="setQuestionImgPath"
          :imagePath="questionClone.imgPath" @image-cleared="clearImg"/>
      <QuestionBuilderYesNo ref="builderChild" v-if="isTrueFalseQuestion" :question="questionClone" />
      <QuestionBuilderMultipleChoice ref="builderChild" v-else-if="isMultipleChoiceQuestion" :question="questionClone" :pendingBusy="pendingBusy"/>
    </div>
  </div>
</template>

<script setup>
import UploadImageComponent from '@/components/UploadImageComponent'
import YesNoQuestion from '@/models/YesNoQuestion'
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import QuestionBuilderYesNo from './QuestionBuilderYesNo.vue'
import QuestionBuilderMultipleChoice from './QuestionBuilderMultipleChoice.vue'
import DeleteButtonComponent from '@/components/buttons/DeleteButtonComponent'
import { inject, ref, onBeforeMount, watchEffect, computed, defineProps, defineEmits } from 'vue'
import { useToast } from 'vue-toast-notification'
import Question from '@/models/Question'
import SaveButtonComponent from '@/components/buttons/SaveButtonComponent'

const props = defineProps({
  question: {
    type: Question,
    required: true
  },
  quizId: {
    type: Number,
    required: true
  }
})

const emit = defineEmits(['saveQuestion', 'deleteQuestion', 'moveQuestion'])

let questionService
if (props.question instanceof YesNoQuestion) {
  questionService = inject('questionTrueFalseService')
} else if (props.question instanceof MultipleChoiceQuestion) {
  questionService = inject('questionMultipleChoiceService')
}

const questionClone = ref(null)
const sdgOptions = ref([])
const uploadImage = ref(null)
const builderChild = ref(null)
const saveQuestionIsPending = ref(false)
const saveQuestionError = ref(null)
const deleteQuestionIsPending = ref(false)
const $toast = useToast()

const cloneQuestion = async () => {
  questionClone.value = await props.question.clone()
}

onBeforeMount(async () => {
  for (let i = 1; i <= 17; i++) {
    sdgOptions.value.push(i)
  }
  await cloneQuestion()
})

const deleteQuestion = () => {
  emit('deleteQuestion', props.question.index - 1)
}

const moveQuestionUp = () => {
  emit('moveQuestion', props.question.index - 1, props.question.index - 2)
}

const moveQuestionDown = () => {
  emit('moveQuestion', props.question.index - 1, props.question.index)
}

const setQuestionImgPath = (imgPath) => {
  questionClone.value.imgPath = imgPath
}

async function callUploadImage () {
  if (uploadImage.value !== null && questionClone.value.imgPath !== null) {
    questionClone.value.imgPath = await uploadImage.value.uploadImage()
  }
}

const clearImg = () => {
  questionClone.value.imgPath = null
}

const saveQuestion = async () => {
  if (!await builderChild.value.validateQuestion()) {
    return
  }

  if (questionClone.value.imgPath !== props.question.imgPath) {
    await callUploadImage()
  }

  const { entity, isPending, error, load } = await questionService.asyncSave(questionClone.value, props.quizId)

  watchEffect(() => {
    saveQuestionIsPending.value = isPending.value
    saveQuestionError.value = error.value
  })

  load().then(() => {
    if (saveQuestionError.value === null) {
      emit('saveQuestion', entity.value)
    } else {
      $toast.error('Could not save question ' + questionClone.value.index)
    }
  })
}

const hasChanged = computed(() => { return !props.question.equals(questionClone.value) && props.question.id !== null })

const pendingBusy = computed(() => { return deleteQuestionIsPending.value || saveQuestionIsPending.value })

const isTrueFalseQuestion = computed(() => { return questionClone.value instanceof YesNoQuestion })

const isMultipleChoiceQuestion = computed(() => { return questionClone.value instanceof MultipleChoiceQuestion })

</script>

<style>
.uploadImageSpace {
  max-width: 400px;
  max-height: 400px;
}

.question-card {
  border: 1.5px solid black;
  border-radius: 15px;
  padding: 20px;
  max-width: 1000px;
  margin: 20px;
}

.question-header {
  display: flex;
  align-items: center;
  justify-content: space-between;
}

.question-number {
  font-size: 24px;
  margin-right: 10px;
  background-color: #e1e1e1;
  border-radius: 10px;
  padding-left: 10px;
  padding-right: 10px;
}

.questionArrowButton {
  width: 30px;
  height: 35px;
  font-size: x-large;
  border-radius: 8px !important;
  padding: 0;
}

.question-type {
  color: #fff !important;
  background-color: #6D3FD9 !important;
  border: 2px solid #6D3FD9 !important;
  border-radius: 4px;
}

.question-type option {
  color: #000 !important;
  background-color: #fff;
}

.question-type:focus {
  box-shadow: none !important;
  outline: none;
}

.question-type:hover {
  background-color: #6D3FD9 !important;
  color: #fff !important;
}

.question-text {
  width: 100%;
  padding: 10px;
  margin-top: 10px;
  height: 80px;
  border: 1px solid #7777779e;
  border-radius: 10px;
  background-color: #f3f3f3;
  resize: none;
  align-content: start;
  font-size: 20px;
}

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

.sdgSelecter .dropdown {
  border: 1px solid #ccc;
  padding: 10px;
  border-radius: 4px;
}

.sdgSelecter .dropdown-item {
  padding: 5px;
  cursor: pointer;
  border-radius: 10px;

}

.sdgSelecter .dropdown-item:hover {
  background-color: #f0f0f0;
}

.sdgSelecter .selected {
  background-color: #6D3FD9;
  border-radius: 10px;
  color: white;
  margin: 0;
}

.sdgSelecter .selected:hover {
  background-color: #6D3FD9;
  border-radius: 10px;
  color: white;
}

</style>
