<template>
  <div class="actionPlan shadow">
    <div class="actionPlanTop">
      <div class="col-9 m-auto">
        <h1 class="h5 paddingLeft10 m-0">{{ props.title }}</h1>
      </div>
      <div class="col justify-content-end m-auto d-flex px-2 overflow-auto">
        <div class="overflow-scroll">
          <img v-for="(value, key) in imgSrcs" :key="key" class="actionPlanSdgImg" alt="..." :src="value">
        </div>
      </div>
    </div>
    <div :id="'actionplan' + props.id" class="actionPlanMiddle">
      <p class="textLeft" v-html="props.description"></p>
    </div>
    <div class="actionPlanFooter">
      <div class="col-4"></div>
      <div class="col-4">
        <button @click="expandActionPlan" class="dropDownButton">
          <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="white" class="bi bi-chevron-down"
               :class="{'flip': expanded}" viewBox="0 0 16 16">
            <path fill-rule="evenodd"
                  d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
          </svg>
        </button>
      </div>
      <div class="col-4 actionPlanBtn justify-content-end m-auto d-flex">
          <button v-if="!isFromProfile" class="btn btn-primary d-inline" @click="addPlan" :disabled="disableButton">
            {{ addPlanButtonText }}
          </button>
          <button v-else class="btn btn-primary d-inline" @click="completePlan" :disabled="disableButton">
            {{ completedPlanButtonText }}
          </button>
      </div>
    </div>
  </div>
</template>

<script setup>
/** This component is used to display an actionplan
 * @author Marco de Boer
 */
import { defineProps, ref, onBeforeMount, defineEmits } from 'vue'
import { sdgData } from '@/assets/testData/sdgTestData'
import useFetch from '@/utils/useFetch'
import CONFIG from '@/app-config'
import { useToast } from 'vue-toast-notification'

const expanded = ref(false)
const disableButton = ref(false)
const toast = useToast()

const emits = defineEmits(['refreshPlans'])

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  description: {
    type: String,
    required: true
  },
  sdgs: {
    type: Array,
    required: true
  },
  id: {
    type: Number,
    required: true
  },
  isFromProfile: {
    type: Boolean,
    required: false,
    default: false
  }
})

const imgSrcs = ref([])
const user = JSON.parse(window.sessionStorage.getItem('ACCOUNT'))
const addPlanIsPending = ref(false)
const addPlanButtonText = ref('Add Plan')
const completedPlanButtonText = ref('Complete')

onBeforeMount(() => {
  for (const sdg of props.sdgs) {
    imgSrcs.value.push(sdgData.find(sdgInarray => sdgInarray.id === sdg).src)
  }
})

const expandActionPlan = () => {
  if (expanded.value) {
    document.querySelector(`#actionplan${props.id}`).style.maxHeight = '150px' // if you change this you also need to change the max-height in the css
    expanded.value = false
  } else {
    document.querySelector(`#actionplan${props.id}`).style.maxHeight = '100%'
    expanded.value = true
  }
}

const addPlan = () => {
  if (user !== null) {
    const body = {
      actionPlanId: props.id,
      userId: user.user_id
    }
    const result = useFetch(`${CONFIG.BACKEND_URL}/actionplans/add`, body, 'POST')
    addPlanIsPending.value = true
    addPlanButtonText.value = 'Adding...'

    result.load().then(() => {
      addPlanIsPending.value = false
      if (result.error.value === null) {
        toast.success('Actionplan added')
        addPlanButtonText.value = 'Added'
        disableButton.value = true
      } else if (result.error.value === 'user already has this action plan') {
        toast.info('Actionplan already added')
        addPlanButtonText.value = 'Added'
        disableButton.value = true
      } else {
        toast.error(result.error.value)
        addPlanButtonText.value = 'Add Plan'
      }
    })
  } else {
    window.location.href = '/login'
  }
}

const completePlan = () => {
  const result = useFetch(`${CONFIG.BACKEND_URL}/users/${user.user_id}/actionplans/${props.id}`, null, 'DELETE')
  completedPlanButtonText.value = 'Completing...'

  result.load().then(() => {
    if (result.error.value === null) {
      toast.success('Actionplan completed')
      completedPlanButtonText.value = 'Completed'
      disableButton.value = true
      emits('refreshPlans', result.data.value)
    } else {
      toast.error(result.error.value)
      completedPlanButtonText.value = 'Complete'
    }
  })
}

</script>

<style>
.textLeft {
  text-align: left !important;
}

.textLeft * {
  text-align: left !important;
}

.paddingLeft10 {
  padding-left: 5px;
  height: 100%;
  text-align: left;
  align-self: center;
}

.actionPlan {
  position: relative;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  height: fit-content;
  padding: 0;
  max-width: 600px;
}

.actionPlanTop {
  background-color: #beabf9;
  color: #FFFFFF;
  max-height: fit-content;
  overflow: unset;
  text-overflow: ellipsis;
  width: 100%;
  border-radius: 10px 10px 0px 0px;
  display: flex;
  text-align: center;
  justify-content: center;
}

.actionPlanMiddle {
  background-color: #FFFFFF;
  min-height: 60px;
  max-height: 150px;
  width: 100%;
  border-radius: 0px 0px 0px 0px;
  display: flex;
  padding: 5px;
  overflow: hidden;
  text-align: start;
}

.actionPlanFooter {
  /* position: absolute;
  bottom: 0; */
  background-color: #beabf9;
  max-height: 45px;
  width: 100%;
  border-radius: 0px 0px 10px 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 5px !important;
}

.dropDownButton:hover {
  border-radius: 100% !important;
  background-color: rgba(151, 71, 225, 0.519) !important;
  padding: 1px;
}

.dropDownButton {
  background-color: transparent !important;
  border: none !important;
}

.flip {
  transform: rotate(180deg);
}

.actionPlanBtn .btn {
  font-size: medium !important;
  padding-top: 2px !important;
  padding-bottom: 2px !important;
}

.actionPlanSdgImg {
  max-height: 30px;
  max-width: 30px;
  margin: 5px;
  object-fit: cover;
}

.overflow-scroll {
  overflow-x: scroll;
  overflow-y: hidden;
  white-space: nowrap;
}

.overflow-scroll::-webkit-scrollbar {
  height: 0px;
  top: -10px;
  width: calc(100% - 48px);
}

.overflow-scroll::-webkit-scrollbar-track {
  background: #b1b3b399;
}

.overflow-scroll::-webkit-scrollbar-thumb {
  background: #29AB87;
}

.overflow-scroll::-webkit-scrollbar-track-piece:start {
  background: #29AB87;
}
</style>
