<template>
    <div class="actionPlan">
        <div class="actionPlanTop">
            <h1 class="">{{ props.title }}</h1>
        </div>
        <div class="actionPlanMiddle">
            <p> {{ props.description }}</p>
        </div>
        <div class="actionPlanFooter">
        </div>
    </div>

</template>
<script setup>
import { defineProps } from 'vue'

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
  }
})

</script>
<style>
.actionPlan {
}

.actionPlanTop {
    background-color: #E0DBF1;
    color: #FFFFFF;
    height: 20%;
    width: 100%;
    border-radius: 10px 10px 0px 0px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.actionPlanMiddle {
    background-color: #FFFFFF;
    height: 60px;
    width: 100%;
    border-radius: 0px 0px 0px 0px;
    display: flex;
    justify-content: center;
    align-items: center;
}

.actionPlanFooter {
    background-color: #E0DBF1;
    height: 40px;
    width: 100%;
    border-radius: 0px 0px 10px 10px;
    display: flex;
    justify-content: center;
    align-items: center;
}

</style>
