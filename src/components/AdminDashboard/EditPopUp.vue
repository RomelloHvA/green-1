<template>
  <div class="popup" v-if="clonedUser">
    <div class="popup-inner">
      <div class="edit-box">

        <form>
          <div class="user-box">
            <input v-model="clonedUser.username" type="text" name="" required="">
            <label type="text" >Username </label>

          </div>
          <div class="user-box">
            <input v-model="clonedUser.email" type="text" name="" required="">
            <label>Email</label>
          </div><center>
          <table>
            <tr>
            <td>Admin:</td>
            <td>
              <div class="form-check">
                <input type="checkbox" class="form-check-input" v-model="clonedUser.isAdmin"
                       @click="handleVerifyPopup">
              </div>
           </td>
            </tr>
          </table>
          <button class="popupButton" @click="toggelPopUp()">
            Close
          </button>
          <button class="popupButton" @click="save" :disabled="!hasChanged">Save</button>
          </center>
        </form>
      </div>
      <AdminVerifyComponent v-if="showVerifyPopup" class="AdminVerifyComponent"
                            @close="handleAdminVerifyClose"
                            @verifiedClose="handleAdminVerifiedClose"
                            user=""></AdminVerifyComponent>
    </div>
  </div>
</template>

<script>
import { User } from '@/models/user'
import AdminVerifyComponent from '@/components/AdminDashboard/AdminVerifyComponent.vue'

export default {
  name: 'EditPopUp',
  components: { AdminVerifyComponent },
  props: {
    toggelPopUp: Function,
    user: {
      type: User,
      required: true
    }
  },
  data () {
    return {
      clonedUser: null,
      showVerifyPopup: false
    }
  },
  async created () {
    if (this.user) {
      this.clonedUser = await this.user.clone()
      console.log(this.clonedUser)
    }
  },
  methods: {
    save () {
      this.$emit('updateUser', this.clonedUser)
      this.toggelPopUp()
    },
    handleVerifyPopup () {
      if (this.clonedUser.isAdmin === true) {
        this.showVerifyPopup = false
        this.clonedUser.isAdmin = false
      } else {
        this.showVerifyPopup = true
      }
    },
    handleAdminVerifyClose () {
      this.showVerifyPopup = false
      this.clonedUser.isAdmin = false
    },
    handleAdminVerifiedClose () {
      this.showVerifyPopup = false
      this.clonedUser.isAdmin = true
    }
  },
  computed: {
    hasChanged () {
      return !User.equals(this.user, this.clonedUser)
    }
  },
  methode: {}
}
</script>

<style scoped>
.popup {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 99;
  background-color: rgba(0, 0, 0, 0.2);

  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-inner {
  background: #FFF;
  padding: 32px;
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
