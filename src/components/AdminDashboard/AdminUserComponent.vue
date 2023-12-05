<template>
  <div class="minvh100">
    <div id="searchbar">
      <form class="form">
        <button class="searchButton">
          <svg width="17" height="16" fill="none" xmlns="http://www.w3.org/2000/svg" role="img"
               aria-labelledby="search">
            <path d="M7.667 12.667A5.333 5.333 0 107.667 2a5.333 5.333 0 000 10.667zM14.334 14l-2.9-2.9"
                  stroke="currentColor" stroke-width="1.333" stroke-linecap="round" stroke-linejoin="round"></path>
          </svg>
        </button>
        <input class="input" placeholder="Type your text" required="" type="text" v-model="searchUser">
      </form>
    </div>
    <table class="table table-sm table-dark">
      <thead>
      <tr>
        <th>UserName</th>
        <th scope="col">Email</th>
        <th scope="col">Action Plan</th>
        <th scope="col">Edit</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="user in users && filteredSearch " :key="user.user_id">
        <td>{{ user.username }}</td>
        <td>{{ user.email }}</td>
        <td>{{ user.user_goal }}</td>
        <td>
          <button id="editButton" @click="() => ToggelPopUp('isEditing', user)">Edit</button>
        </td>
      </tr>
      <!-- Edit dropdown that is displayed when the "Edit" button is clicked -->
      </tbody>
      <EditPopUp @updateUser="updateUser" v-if="popUpTrigger.isEditing" :user="selectedUser"
                 :toggelPopUp="() => ToggelPopUp('isEditing')">
      </EditPopUp>
    </table>
  </div>
</template>

<script>
import { ref } from 'vue'
import EditPopUp from '@/components/AdminDashboard/EditPopUp'

export default {
  name: 'AdminUserComponent',
  components: { EditPopUp },
  inject: ['usersServices'],

  data () {
    return {
      users: [],
      searchUser: ''
    }
  },
  setup () {
    const selectedUser = ref(null)
    const popUpTrigger = ref({
      isEditing: false
    })

    const ToggelPopUp = (trigger, user) => {
      popUpTrigger.value[trigger] = !popUpTrigger.value[trigger]
      selectedUser.value = user
    }
    return {
      EditPopUp,
      popUpTrigger,
      ToggelPopUp,
      selectedUser
    }
  },
  computed: {
    filteredSearch () {
      return this.users.filter(user => {
        // console.log(this.user)
        return (
          (user.username && user.username.toLowerCase().indexOf(this.searchUser.toLowerCase()) > -1) ||
          (user.email && user.email.toLowerCase().indexOf(this.searchUser.toLowerCase()) > -1) ||
          (user.user_goal && user.user_goal.toLowerCase().indexOf(this.searchUser.toLowerCase()) > -1)
        )
      })
    }
  },
  async created () {
    this.users = await this.usersServices.asyncFindAll()
  },
  methods: {
    saveEdit () {
      this.isEditing = false
    },
    async updateUser (updatedUser) {
      try {
        const index = this.users.findIndex(o => o.user_id === updatedUser.user_id)
        if (index !== -1) {
          this.users.splice(index, 1, updatedUser)
        }
        await this.usersServices.asyncSave(updatedUser)
      } catch (error) {
        console.log(error)
      }
    }
  }
}

</script>

<style scoped>
#editButton {
  --color: #560bad;
  font-family: inherit;
  display: inline-block;
  width: 8em;
  height: 2.6em;
  line-height: 2.5em;
  position: relative;
  overflow: hidden;
  border: 2px solid var(--color);
  transition: color .5s;
  z-index: 1;
  font-size: 17px;
  border-radius: 6px;
  font-weight: 500;
  color: var(--color);
}

#editButton:before {
  content: "";
  position: absolute;
  z-index: -1;
  background: var(--color);
  height: 150px;
  width: 200px;
  border-radius: 50%;
}

#editButton:hover {
  color: #fff;
}

#editButton:before {
  top: 100%;
  left: 100%;
  transition: all .7s;
}

#editButton:hover:before {
  top: -30px;
  left: -30px;
}

#editButton:active:before {
  background: #3a0ca3;
  transition: background 0s;
}

/* From uiverse.io by @satyamchaudharydev */
/* removing default style of button */

.form .searchButton {
  border: none;
  background: none;
  color: #8b8ba7;
}

/* styling of whole input container */
.form {
  --timing: 0.3s;
  --width-of-input: 200px;
  --height-of-input: 40px;
  --border-height: 2px;
  --input-bg: white;
  --border-color: #2f2ee9;
  --border-radius: 30px;
  --after-border-radius: 1px;
  position: relative;
  width: 500px;
  height: var(--height-of-input);
  display: flex;
  padding-inline: 0.8em;
  border-radius: var(--border-radius);
  transition: border-radius 0.5s ease;
  background: var(--input-bg, #fff);
  margin: 5px;
}

/* styling of Input */
.input {
  font-size: 0.9rem;
  background-color: transparent;
  width: 100%;
  height: 100%;
  padding-inline: 0.5em;
  padding-block: 0.7em;
  border: none;
}

/* styling of animated border */
.form:before {
  content: "";
  position: absolute;
  background: var(--border-color);
  transform: scaleX(0);
  transform-origin: center;
  width: 100%;
  height: var(--border-height);
  left: 0;
  bottom: 0;
  border-radius: 1px;
  transition: transform var(--timing) ease;
}

/* Hover on Input */
.form:focus-within {
  border-radius: var(--after-border-radius);
}

input:focus {
  outline: none;
}

/* here is code of animated border */
.form:focus-within:before {
  transform: scale(1);
}

/* styling of close button */
/* == you can click the close button to remove text == */
.reset {
  border: none;
  background: none;
  opacity: 0;
  visibility: hidden;
}

/* close button shown when typing */
input:not(:placeholder-shown) ~ .reset {
  opacity: 1;
  visibility: visible;
}

/* sizing svg icons */
.form svg {
  width: 17px;
  margin-top: 3px;
}

#searchbar {
  background-color: gray;
  height: 50px;
  padding: 1px;

}
</style>
