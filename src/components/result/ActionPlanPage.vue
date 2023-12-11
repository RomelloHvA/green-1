<template>
  <div class="container">
    <div class="container">
      <div class="column">
        <div class="col">
          <h3>{{ title }}</h3>
          <img :src="require(`@/assets/SDG-Icons/${imgPath}`)" class="goalImage" alt="Loading">
        </div>
        <div class="mt-5 col">
          <ul class="actionPlanList">
            <li v-for="action in actionPlan" :key="action.id">
              <div class="container">
                <h5 class="actionPlansDescription mt-4">{{ action.description }}</h5>
              </div>
            </li>
          </ul>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { useRoute } from 'vue-router'
import { watch, ref, inject, onBeforeMount } from 'vue'

export default {
  name: 'ActionPlanPage',
  /**
   * Injects the action plan service and the profile service
   * @return {{actionPlan: Ref<UnwrapRef<[{sdgId: number, description: string, id: number, title: string},{sdgId: number, description: string, id: number, title: string},{sdgId: number, description: string, id: number, title: string},{sdgId: number, description: string, id: number, title: string}]>>, imgPath: Ref<UnwrapRef<string>>, title: Ref<UnwrapRef<string>>}}
   */
  setup () {
    const title = ref('Goal Title')
    const imgPath = ref('SDG Wheel_WEB.png')
    const actionPlan = ref([
      {
        id: 1,
        sdgId: 1,
        title: 'Action Plan 1',
        description: 'This is the description of the action plan'
      },
      {
        id: 2,
        sdgId: 1,
        title: 'Action Plan 2',
        description: 'This is the description of the action plan'
      },
      {
        id: 3,
        sdgId: 1,
        title: 'Action Plan 3',
        description: 'This is the description of the action plan'
      },
      {
        id: 4,
        sdgId: 2,
        title: 'Action Plan 4',
        description: 'This is the description of the action plan'
      }
    ])
    const route = useRoute()
    const routeGoalId = ref(route.params.goalId)
    const actionPlanService = inject('actionPlanService')
    const profileService = inject('profileService')

    /**
     * Fetches the data from the API
     * @return {Promise<void>}
     */
    const fetchData = async () => {
      try {
        const profile = await profileService.asyncFindById(0)
        const goals = profile.goals
        console.log(goals)
        console.log(routeGoalId.value)

        const selectedGoal = goals.find((goal) => goal.sdgId === parseInt(routeGoalId.value))
        console.log(selectedGoal)

        if (selectedGoal) {
          const APIResult = selectedGoal
          console.log(APIResult)
          const APIResult2 = await actionPlanService.asyncFindBySdgId(routeGoalId.value)

          console.log(imgPath.value)
          imgPath.value = APIResult.image
          title.value = APIResult.title

          console.log(actionPlan.value)
          actionPlan.value = APIResult2
          console.log(actionPlan.value)
        } else {
          console.error('Selected goal not found.')
          // Handle the case when the selected goal is not found
        }
      } catch (error) {
        console.error('Error fetching data:', error)
        // Handle errors if necessary
      }
    }
    /**
     * Fetches the data when the component is mounted
     */
    onBeforeMount(() => {
      if (routeGoalId.value) {
        fetchData()
      }
    })

    /**
     * Watches the route for changes and fetches the data
     */
    watch(() => route.params.goalId, (newGoalId) => {
      console.log('Route Changed:', route.params.goalId)
      routeGoalId.value = newGoalId
      fetchData()
    })
    // Return the data
    return {
      imgPath,
      title,
      actionPlan
    }
  }
}
</script>

<style scoped>
h3 {
  font-weight: bold;
}

.goalImage {
  width: 25%;
  height: 25%;
  object-fit: contain;
}

.actionPlanList {
  list-style-type: none; /* Remove default list styling */
  padding: 0; /* Remove default padding */
  margin: 0; /* Remove default margin */
}

.actionPlanList li {
  border-bottom: 1px solid #ccc; /* Add a bottom border to create a separation line */
  padding-bottom: 0.5rem; /* Optional: Add some padding to improve spacing */
}
</style>
