<template>
    <div class="d-flex row row-gap-4 overflowScroll">
        <p v-if="actionPlans.length === 0"> Looking kinda empty...</p>
        <ActionPlan v-for="actionPlan in actionPlans" :key="actionPlan.id"
        :id="actionPlan.id"
        :title="actionPlan.title"
        :description="actionPlan.description"
        :sdgs="actionPlan.sdgArray"
        :is-from-profile="true"
        @refreshPlans="refreshPlans"
        />
    </div>
</template>
<script setup>
import ActionPlan from '@/components/quizResultsComponents/ActionPlan.vue'
import useFetch from '@/utils/useFetch'
import { ref, onBeforeMount } from 'vue'
import CONFIG from '@/app-config'

const actionPlans = ref([])
const user = JSON.parse(window.sessionStorage.getItem('ACCOUNT'))

onBeforeMount(() => {
  const result = useFetch(`${CONFIG.BACKEND_URL}/users/${user.user_id}/actionplans`, 'GET')

  result.load().then(data => {
    console.log()
    actionPlans.value = result.data.value
  })
})

const refreshPlans = (actionPlansFromBackend) => {
  actionPlans.value = actionPlansFromBackend
}

</script>
<style>

.overflowScroll {
    overflow-y: scroll;
    max-height: 600px;
}

/* //style ::-webkit-scrollbar */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    background: #f1f1f1;
}

/* Handle */
::-webkit-scrollbar-thumb {
    background: #888;
}

</style>
