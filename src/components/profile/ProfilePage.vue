<template>
  <div class="mt-5 mb-5 profileBody">
    <form>
      <div class="container text-center">
        <div class="row align-items-start">
          <div class="col">
            <div class="container">
              <div class="container-md">
                <img v-if="item.profilePic" :src="profile.photo" class="pfp" alt="...">
                <img v-else src="@/assets/photo/profielfoto-silhouet.jpg" class="pfp" alt="...">
                <div class="mb-3" style="margin: 1rem;">
                  <input type="file" accept="image/*" class="form-control" id="inputGroupFile02" @change="onChange">
                </div>
                <div class="mb-3">
                  <label for="inputName" class="form-label">First Name: </label>
                  <input type="text" autocomplete="off" class="form-control" id="inputFirstName"
                         v-model="profile.first_name"
                         required>
                  <div v-if="isFirstNameEmpty" class="invalid-message border mt-1 error">
                    Name is required
                  </div>
                </div>
                <div class="mb-3">
                  <label for="inputName" class="form-label">Last Name: </label>
                  <input type="text" autocomplete="off" class="form-control" id="inputLastName"
                         v-model="profile.last_name"
                         required>
                  <div v-if="isLastNameEmpty" class="invalid-message border mt-1 error">
                    Name is required
                  </div>
                </div>
                <div class="mb-3">
                  <label for="inputBirthday" class="form-label">Birthday: </label>
                  <input type="date" min="1900-01-01" class="form-control" id="inputBirthday"
                         v-model="profile.date_of_birth"
                         required>
                  <div v-if="isBirthEmpty" class="invalid-message border mt-1 error">
                    Birthday is required
                  </div>
                </div>
                <div class="mb-3">
                  <label for="inputOccupation" class="form-label">Occupation: </label>
                  <input type="text" autocomplete="off" class="form-control" id="inputOccupation"
                         v-model="profile.occupation" aria-required="true" required>
                </div>
                <div v-if="isOccupationEmpty" class="invalid-message border mt-1 error">
                  Occupation is required
                </div>
                <button @click="saveEdit" type="submit" class="btn btn-primary editButton">Save</button>
                <button @click="cancelEdit" type="button" class="btn btn-primary editButton">Cancel</button>
              </div>
            </div>
          </div>
          <div class="col">
            <div class="mb-3">
              <label for="inputBio" class="form-label">Bio: </label>
              <textarea class="form-control" id="inputBio" autocomplete="off"
                        style="height: 25rem; width: 100%; resize: none" v-model="profile.bio" required></textarea>
              <div v-if="isBioEmpty" class="invalid-message border mt-1 error">
                Bio is required
              </div>
            </div>
          </div>
          <div class="col">
            <div class="mb-3">
              <label for="inputGoals">Goals</label>
            </div>
            <div class="container goalContainer">
              <div v-for="goal in profile.goals" :key="goal.id">
                <div class="container border rounded-4 mb-4 goals">
                  <img :src="getGoalImage(goal.image)" class="col-1 goalImage mt-2" alt="...">
                  <h6 class="col-6 goalTitle">{{ goal.title }}</h6>
                  <img @click="deleteGoal(goal.id)" src="@/assets/img/trashcan/trash-can.png"
                       class="col-2 deleteGoalButton">
                </div>
              </div>
            </div>
            <div v-if="isGoalEmpty" class="invalid-message border mt-1 error">
              Goal is required
            </div>
            <div class="row mt-1">
              <div class="col-5">
                <label for="selectList">Select a goal:</label>
              </div>
              <div class="col">
                <select class="form-select selectGoal" v-model="selectedOption">
                  <option v-for="goal in sdgGoals" :key="goal.id" :value="goal.sdgId">
                    {{ goal.title }}
                  </option>
                </select>
              </div>
            </div>
            <button @click="showResults" type="button" class="btn btn-primary resultButton">See Quiz Results</button>
            <button @click="createGoal" type="button" class="btn btn-primary addButton">Add Goals</button>
            <div v-if="showGoalSDGEmpty" class="invalid-message border mt-1 error">
              Please select a valid goal.
            </div>
            <div v-if="showGoalSDGLimit" class="invalid-message border mt-1 error">
              You can't have more than three goals at the time.
            </div>
            <div v-if="showGoalSDGDup" class="invalid-message border mt-1 error">
              You can't add the same goal.
            </div>
            <div v-if="showQuizResultVal" class="invalid-message border mt-1 error">
              You don't have any quiz results.
            </div>
          </div>
        </div>
      </div>
    </form>
  </div>
</template>

<script>
import { Profile } from '@/models/profile'
import { Goal } from '@/models/goal'

/**
 * ProfilePage compontent
 * This is the page is used so users view their profile information
 * and make changes to them.
 */

export default {
  name: 'ProfilePage',
  inject: ['profileService'],
  data () {
    return {
      profile: null,
      selectedOption: '',
      sdgGoals: [
        {
          id: 1,
          sdgId: 1,
          title: 'No Poverty'
        },
        {
          id: 2,
          sdgId: 2,
          title: 'Zero Hunger'
        },
        {
          id: 3,
          sdgId: 3,
          title: 'Good Health and Well Being'
        },
        {
          id: 4,
          sdgId: 4,
          title: 'Quality Education'
        },
        {
          id: 5,
          sdgId: 5,
          title: 'Gender Equality'
        },
        {
          id: 6,
          sdgId: 6,
          title: 'Clean Water and Sanitation'
        },
        {
          id: 7,
          sdgId: 7,
          title: 'Affordable and Clean Energy'
        },
        {
          id: 8,
          sdgId: 8,
          title: 'Decent Work and Economic Growth'
        },
        {
          id: 9,
          sdgId: 9,
          title: 'Industry, Innovation, and Infrastructure'
        },
        {
          id: 10,
          sdgId: 10,
          title: 'Reduced Inequality'
        },
        {
          id: 11,
          sdgId: 11,
          title: 'Sustainable Cities and Communities'
        },
        {
          id: 12,
          sdgId: 12,
          title: 'Responsible Consumption and Production'
        },
        {
          id: 13,
          sdgId: 13,
          title: 'Climate Action'
        },
        {
          id: 14,
          sdgId: 14,
          title: 'Life Below Water'
        },
        {
          id: 15,
          sdgId: 15,
          title: 'Life on Land'
        },
        {
          id: 16,
          sdgId: 16,
          title: 'Peace, Justice, and Strong Institutions'
        },
        {
          id: 17,
          sdgId: 17,
          title: 'Partnerships for the Goals'
        }
      ],
      item: {
        image: null,
        profilePic: null
      },
      oldProfileData: null,
      showGoalSDGEmpty: false,
      showGoalSDGLimit: false,
      showGoalSDGDup: false,
      showQuizResultVal: false
    }
  },
  /**
   * Function will create a copy of the profile when the page is loaded
   */
  async created () {
    try {
      // Wait for the asynchronous operation to complete
      this.profile = new Profile()
      // const profileService = await this.profileService.asyncFindAll()
      const profileService = await this.profileService.asyncFindById(0)

      this.profile = profileService
      // Log the fetched profile data
      console.log(this.profile)

      // Create a copy of the profile
      this.oldProfileData = Profile.copyConstructor(this.profile)
      console.log(this.oldProfileData)
    } catch (error) {
      console.error('Error fetching profile data:', error)
    }
  },
  methods: {
    /**
     * Function to show the newly uploaded profile picture
     * @param e
     */
    onChange (e) {
      // Changes the image to the newly inputted image
      const file = e.target.files[0]
      this.item.profilePic = URL.createObjectURL(file)
      this.profile.photo = this.item.profilePic
    },
    getGoalImage (goal) {
      // Returns the image of the goal
      return require(`@/assets/SDG-Icons/${goal}`)
    },
    /**
     * Function to delete a goal
     * @param id
     */
    deleteGoal (id) {
      // Deletes a goal from the list
      console.log(this.profile.goals)
      this.profile.goals = this.profile.goals.filter((goal) => goal.id !== id)
      console.log(this.profile.goals)
      id = null
    },
    /**
     * Function will add a new goal to the list
     * It will check if a goal is selected and if it is it will create a new goal
     * and add it to the list
     */
    createGoal () {
      const goalLength = this.profile.goals.length
      const maxGoalLength = 3
      const increment = 1
      let lastId = 0
      if (goalLength === 0) {
        lastId = 0
      } else {
        lastId = this.profile.goals.reduce((maxId, goal) => {
          return goal.id > maxId ? goal.id : maxId
        }, 0)
      }
      const newId = lastId + increment
      const selectedSDG = this.sdgGoals.find(goal => goal.sdgId === this.selectedOption)
      // Check if the user has more than three goals
      if (goalLength >= maxGoalLength) {
        this.showGoalSDGLimit = true
      } else {
        this.showGoalSDGLimit = false
        // Check if the user has already added the same goal
        if (this.profile.goals.find(goals => goals.sdgId === this.selectedOption)) {
          this.showGoalSDGDup = true
        } else {
          this.showGoalSDGDup = false
          // Validation check
          if (selectedSDG) {
            // Create a new goal with the selected SDG title and image
            const newGoal = new Goal(newId, this.profile.user_id, selectedSDG.title, selectedSDG.sdgId)
            // newGoal.title = selectedSDG
            this.profile.goals.push(newGoal)

            // Clear the selectedOption after adding the goal
            this.selectedOption = ''
            this.showGoalSDGEmpty = false
            console.log(this.profile.goals)
          } else {
            this.showGoalSDGEmpty = true
          }
        }
      }
    },
    /**
     * Function will show the quiz results
     */
    showResults () {
      if (this.profile.goals.length === 0) {
        this.showQuizResultVal = true
      } else {
        this.showQuizResultVal = false
        this.$router.push({ path: '/results' })
      }
    },
    /**
     * Function will undo any changes that has been made
     */
    cancelEdit () {
      // Check if the user wants to undo the changes
      if (confirm('Are you sure you want to undo your changes?') === true) {
        const firstName = this.oldProfileData.first_name
        const lastName = this.oldProfileData.last_name
        const photo = this.oldProfileData.photo
        const birth = this.oldProfileData.date_of_birth
        const occupation = this.oldProfileData.occupation
        const bio = this.oldProfileData.bio
        const goals = this.oldProfileData.goals
        this.profile.firstName = firstName
        this.profile.lastName = lastName
        this.profile.photo = photo
        this.profile.birth = birth
        this.profile.occupation = occupation
        this.profile.bio = bio
        this.profile.goals = goals
        alert('Changes have been undone')
      }
    },
    /**
     * Function to save changes
     */
    async saveEdit () {
      try {
        // Check if firstname, lastname, birth, occupation, bio and goals are not empty
        if (this.profile.first_name === '' ||
          this.profile.last_name === '' ||
          this.profile.date_of_birth === '' ||
          this.profile.occupation === '' ||
          this.profile.bio === '') {
          return alert('Some of the fields are empty')
        } else if (this.profile.goals.length === 0) {
          return alert('Please add a goal')
        } else {
          // Check if the user wants to save the changes
          if (confirm('Are you sure you want to save changes?') === true) {
            const id = this.profile.user_id
            const firstName = this.profile.first_name
            const lastName = this.profile.last_name
            const photo = this.profile.photo
            const birth = this.profile.date_of_birth
            const occupation = this.profile.occupation
            const bio = this.profile.bio
            const goals = this.profile.goals

            // Overwrite old data with newely input data
            this.profile = new Profile(id, firstName, lastName, photo, birth, occupation, bio, goals)
            try {
              // Wait for the asynchronous operation to complete
              const savedProfile = await this.profileService.asyncSave(this.profile)

              // Check if the profile has been saved
              if (savedProfile) {
                this.profile = savedProfile
                alert('Changes have been saved')
                // Creates a new copy of the profile
                this.oldProfileData = Profile.copyConstructor(this.profile)
              } else {
                // If the profile has not been saved, show an error
                alert('Failed to save changes. Please check the console for errors.')
              }
            } catch (error) {
              console.error('Error saving profile data:', error)
              alert('Failed to save changes. Please check the console for errors.')
            }
            alert('Changes have been saved')
            // Creates a new copy of the profile
            this.oldProfileData = Profile.copyConstructor(this.profile)
            this.$router
              .push({ path: '/' })
          }
        }
      } catch (error) {
        console.error('Error saving profile data:', error)
      }
    }
  },
  computed: {
    /**
     * Validation checks
     * @return {boolean}
     */
    isFirstNameEmpty () {
      // Check if the name is an empty string
      return this.profile.first_name === ''
    },
    isLastNameEmpty () {
      // Check if the name is an empty string
      return this.profile.last_name === ''
    },
    // Check if the birth is an empty string
    isBirthEmpty () {
      return this.profile.date_of_birth === ''
    },
    // Check if the occupation is an empty string
    isOccupationEmpty () {
      return this.profile.occupation === ''
    },
    // Check if the bio is an empty string
    isBioEmpty () {
      return this.profile.bio === ''
    },
    // Check if the goal is an empty array
    isGoalEmpty () {
      return this.profile.goals === 0
    }
  }
}
</script>

<style scoped>
.pfp {
  width: 7.5rem;
  height: 7.5rem;
  border-radius: 60px;
}

.resultButton {
  margin: 0.5rem;
  width: 10rem;
  border-radius: 20px;
  align-self: center;
}

.addButton {
  margin: 0.5rem;
  width: 10rem;
  border-radius: 20px;
  align-self: center;
}

.editButton {
  margin: 0.5rem;
  width: 7.5rem;
  border-radius: 20px;
  align-self: center;
}

.goalContainer {
  max-height: 25rem;
  overflow: auto
}

.deleteGoalButton {
  margin-left: auto;
  width: auto;
  height: 50%;
  align-self: center;
}

.deleteGoalButton:hover {
  cursor: pointer;
}

.goalImage {
  width: auto;
  height: 80%;
}

.goals {
  display: flex;
  flex-direction: row;
  width: 100%;
  height: 5rem;
}

.goalTitle {
  margin-top: 1rem;
  margin-left: auto;
  text-align: center;
  align-self: center;
}

.selectGoal:hover {
  cursor: pointer;
}

.error {
  color: red;
}
</style>
