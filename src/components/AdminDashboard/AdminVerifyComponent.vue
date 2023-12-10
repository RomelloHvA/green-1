<template>
  <div class="popUpAdminContainer">
    <div class="popUpAdminInput">
      <table class="tableContainer">
        <tr>
          <td>Username:</td>
          <td><input type="text" v-model="usernameInput"></td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><input type="password" v-model="passwordInput"></td>
        </tr>
      </table>
    </div>
    <div class="popUpAdminButton">
      <button @click="closePopup" class="popupButton">Close</button>
      <button @click="checkAdmin" class="popupButton">Verify</button>
    </div>
  </div>
</template>

<script>
// import { User } from '@/models/user'

export default {
  name: 'AdminVerifyComponent',
  inject: ['usersServices'],
  data () {
    return {
      listOfUsers: [],
      isAdmin: false,
      usernameInput: '',
      passwordInput: ''
    }
  },
  async created () {
    try {
      this.listOfUsers = await this.usersServices.asyncFindAll()
      console.log(this.listOfUsers)
    } catch (error) {
      console.log(error)
    }
    this.listOfUsers = this.listOfUsers.filter(user => user.isAdmin)
  },
  methods: {
    closePopup () {
      if (!this.isAdmin) {
        this.$emit('close')
      }
    },
    checkAdmin () {
      try {
        const foundUser = this.listOfUsers.find(user => user.username === this.usernameInput && user.password === this.passwordInput)
        if (foundUser && foundUser.isAdmin === true) {
          this.isAdmin = true
          this.$emit('verifiedClose')
        } else {
          this.isAdmin = false
          alert('You are not an admin')
        }
      } catch (error) {
        console.log(error)
        this.isAdmin = false
        alert('You are not an admin')
      }
    }
  }
}
</script>

<style scoped>
.popUpAdminContainer {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 25em;
  height: 12em;
  z-index: 100;
  display: flex;
  flex-direction: column;
  background: #FFFFFF;
}

.tableContainer {
  background: #1d1d1d;
}

.popUpAdminInput {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center; /* Center vertically */
  margin-top: 1.5em;
}

.popupButton {
  background: #1d1d1d;
  color: #FFFFFF;
}

.popUpAdminButton {
//margin-top: -1.9em; display: flex;
  align-items: center;
  justify-content: center;
}
</style>
