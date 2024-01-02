<template>
    <div class="actionPlan shadow">
        <div class="actionPlanTop">
            <h1 class="h2">{{ props.title }}</h1>
        </div>
        <div :id="'actionplan' + props.id" class="actionPlanMiddle">
            <p> {{ props.description }}</p>
        </div>
        <div class="actionPlanFooter">
          <div class="col-4"></div>
          <div class="col-4">
            <button @click="expandActionPlan" class="dropDownButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="white" class="bi bi-chevron-down" :class="{'flip': expanded}" viewBox="0 0 16 16">
              <path fill-rule="evenodd" d="M1.646 4.646a.5.5 0 0 1 .708 0L8 10.293l5.646-5.647a.5.5 0 0 1 .708.708l-6 6a.5.5 0 0 1-.708 0l-6-6a.5.5 0 0 1 0-.708z"/>
            </svg>
          </button>
          </div>
          <div class="col-4 actionPlanBtn justify-content-end m-auto d-flex">
            <button class="btn btn-primary ">
                Add Plan
            </button>
          </div>
        </div>
    </div>
</template>
<script setup>
import { defineProps, ref } from 'vue'
const expanded = ref(false)

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
  }
})

const expandActionPlan = () => {
  if (expanded.value) {
    document.querySelector(`#actionplan${props.id}`).style.maxHeight = '60px'
    expanded.value = false
  } else {
    document.querySelector(`#actionplan${props.id}`).style.maxHeight = '100%'
    expanded.value = true
  }
}

</script>
<style>
.actionPlan {
  position: relative;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  height: fit-content;
  padding: 0;
}

.actionPlanTop {
    background-color: #E0DBF1;
    color: #FFFFFF;
    max-height: 40px;
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
    max-height: 60px;
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
    background-color: #E0DBF1;
    max-height: 45px;
    width: 100%;
    border-radius: 0px 0px 10px 10px;
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 5px !important ;
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

</style>
