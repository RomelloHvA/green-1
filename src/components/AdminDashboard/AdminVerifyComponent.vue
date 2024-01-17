<template>
  <div class="popUpAdminContainer">
    <div class="edit-box">

      <form>
        <div class="user-box">
          <input v-model="usernameInput" id="verifyUsernameInput" type="text" name="" required="">
          <label>Username</label>
        </div>
        <div class="user-box">
          <input v-model="passwordInput" id="verifyPasswordInput" type="password" name="" required="">
          <label>Password</label>
        </div>
        <button @click="closePopup" id="closePopupAdminVerify" class="popupButton">Close</button>
        <button @click="checkAdmin" id="checkPopupAdminVerify" class="popupButton">Verify</button>
      </form>
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
        console.error(error)
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

.popupButton {
  background: #1d1d1d;
  color: #FFFFFF;
}

.edit-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(24, 20, 20, 0.987);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0,0,0,.6);
  border-radius: 10px;
}

.edit-box .user-box {
  position: relative;
}

.edit-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}

.edit-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: .5s;
}

.edit-box .user-box input:focus ~ label,
.edit-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #bdb8b8;
  font-size: 12px;
}

.edit-box form a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #ffffff;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: .5s;
  margin-top: 40px;
  letter-spacing: 4px
}

.edit-box a:hover {
  background: #03f40f;
  color: #fff;
  border-radius: 5px;
  box-shadow: 0 0 5px #03f40f,
  0 0 25px #03f40f,
  0 0 50px #03f40f,
  0 0 100px #03f40f;
}

.edit-box a span {
  position: absolute;
  display: block;
}

@keyframes btn-anim1 {
  0% {
    left: -100%;
  }

  50%,100% {
    left: 100%;
  }
}

.edit-box a span:nth-child(1) {
  bottom: 2px;
  left: -100%;
  width: 100%;
  height: 2px;
  background: linear-gradient(90deg, transparent, #03f40f);
  animation: btn-anim1 2s linear infinite;
}
</style>
