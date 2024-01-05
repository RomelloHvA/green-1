<template>
  <section class="vh-100 bg-image">
    <div class="mask d-flex align-items-center h-100 background">
      <div class="container h-100">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-9 col-lg-7 col-xl-6">
            <div class="card" style="border-radius: 15px;">
              <div class="loginBox p-5">
                <h2 class="text-uppercase text-center mb-5">Log In</h2>
                <form v-on:submit.prevent>
                  <div class="form-outline mb-4">
                    <input type="text" id="userName" class="form-control" placeholder="Username" v-model="inputUserName"/>
                  </div>
                  <div class="form-outline mb-4">
                    <input type="password" id="password" class="form-control" placeholder="Password"
                           v-model="inputPassWord"/>
                    <p class="text-danger">{{ errorMessage }}</p>
                  </div>
                  <div class="d-flex justify-content-center">
                    <button type="submit" id="loginButton"
                            @click="handleLogin" class="btn btn-primary btn-block btn-lg buttonColor text-body">Log In
                    </button>
                  </div>
                  <p class="text-center text-muted mt-4 mb-0">Don't have an account?
                    <router-link class="fw-bold text-body signuplink" to="/signup">Sign up here</router-link>
                  </p>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </section>
</template>

<script>
import router from '@/router'
import eventBus from 'vue-toast-notification/src/js/bus'

export default {
  name: 'LogInView',
  inject: ['sessionService'],
  data () {
    return {
      errorMessage: '',
      inputUserName: '',
      inputPassWord: ''
    }
  },

  methods: {
    async handleLogin () {
      try {
        console.log('Login data:', this.inputUserName, this.inputPassWord)
        const response = await this.sessionService.asyncLogin(this.inputUserName, this.inputPassWord)
        console.log('LoginView response:', response)
        if (response) {
          // Emitting the username to NavBar so the NavBar knows the user is logged in
          await eventBus.emit('change-data', this.inputUserName)
          // Redirects the user back to home after successful login
          await router.push({ name: 'home' })
        } else {
          throw new Error('Something went wrong: ' + 'Invalid username or password')
        }
      } catch (error) {
        console.error(error)
        this.errorMessage = error.message
      }
    }
    // /**
    //  * This method is responsible for handling user-login
    //  * @author Jiaming Yan
    //  * @return {Promise<void>}
    //  */
    // async handleLogin () {
    //   const userData = {
    //     username: this.userName,
    //     password: this.passWord
    //   }
    //   try {
    //     const response = await fetch(CONFIG.BACKEND_URL + '/users/login', {
    //       method: 'POST',
    //       headers: { 'Content-Type': 'application/json' },
    //       body: JSON.stringify(userData)
    //     })
    //     console.log('Login response:', response)
    //     console.log(userData)
    //     if (response.ok) {
    //       console.log('Login successful:', response.status)
    //       sessionStorage.setItem('username', this.userName)
    //       // Emitting the username to NavBar so the NavBar knows the user is logged in
    //       eventBus.emit('change-data', this.username)
    //       // Redirects the user back to home after successful login
    //       await router.push({ name: 'home' })
    //     } else {
    //       throw new Error('Something went wrong: ' + await response.text())
    //     }
    //   } catch (error) {
    //     console.error()
    //     this.errorMessage = error.message
    //   }
    // }
    // A hardcoded account that redirects to the profile page
    // logInCheck () {
    //   if (this.userName === 'Santosh1234' && this.password === 'santosh') {
    //     this.errorMessage = ''
    //     router.push({ name: 'profile' })
    //   } else {
    //     this.errorMessage = 'Please check that you have used the correct email address and password and try again.'
    //   }
    // }
  }
}

</script>

<style scoped>
.signuplink {
  text-decoration: none;
}

.signuplink:hover {
  text-decoration: underline;
}

.background {
  background: #401B96;
  /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
  background: linear-gradient(359deg, #401B96 4.87%, rgba(129, 180, 239, 0.93) 53.59%, #401B96 94.99%);
}

.buttonColor {
  /* W3C, IE 10+/ Edge, Firefox 16+, Chrome 26+, Opera 12+, Safari 7+ */
}
</style>
