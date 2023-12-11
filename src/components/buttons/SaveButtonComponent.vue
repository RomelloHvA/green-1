<template>
    <button type="button" :class="{ 'disabled': disabled }" @click="saveQuestion()"
        class="btn btn-success m-1">
        <div class="d-flex row">
            <div class="col">
                {{ saveButtonText }}
            </div>
            <div v-if="isPending" class="col spinnerInButton p-0">
                <div class="spinner-border text-light spinnerInButton" role="status">
                    <span class="sr-only"></span>
                </div>
            </div>
        </div>
    </button>
</template>

<script setup>
import { computed, defineProps, defineEmits } from 'vue'

const props = defineProps({
  disabled: {
    type: Boolean,
    required: true
  },
  isPending: {
    type: Boolean,
    required: true
  }
})

const emit = defineEmits(['save'])

const saveButtonText = computed(() => {
  if (props.isPending) {
    return 'Saving'
  } else if (props.disabled) {
    return 'Saved'
  } else {
    return 'Save'
  }
})

const saveQuestion = () => {
  emit('save')
}

</script>

<style>
.spinnerInButton {
  max-height: 25px;
  max-width: 25px;
  margin-right: 5px !important;
}
</style>
