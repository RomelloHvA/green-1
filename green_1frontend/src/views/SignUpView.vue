<template>
  <section class="bg-image">
    <div class="mask d-flex align-items-center background">
      <div class="container p-5">
        <div class="row d-flex justify-content-center align-items-center h-100">
          <div class="col-12 col-md-9 col-lg-7 col-xl-6">
            <div class="card" style="border-radius: 15px;">
              <div class="card-body p-5">
                <h2 class="text-uppercase text-center mb-5">Sign Up</h2>
                <form>
                  <!-- row -->
                  <div class="row">
                    <div class="col-md-6 mb-4">
                      <div class="form-outline">
                        <input v-model="firstName" type="text" class="form-control" id="firstName" placeholder="First name" />
                      </div>
                    </div>
                    <div class="col-md-6 mb-4">
                      <div class="form-outline">
                        <input v-model="lastName" type="text" class="form-control" id="lastName" placeholder="Last name" />
                      </div>
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="emailBox" type="email" class="form-control" id="emailBox" placeholder="Your Email" />
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="educationBox" type="text" class="form-control" id="educationBox" placeholder="Occupation" />
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="postCodeBox" type="text" class="form-control" id="postCodeBox" placeholder="ZIP code/Postal code" />
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="userName" type="text" class="form-control" id="userName" placeholder="Username*" />
                    <p class="text-danger">{{ errorMessageName }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="password" type="password" class="form-control" id="password" placeholder="Password*" />
                    <p class="text-danger">{{ errorMessagePassword }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <input v-model="repeatPassword" type="password" class="form-control" id="repeatPasswordBox" placeholder="Repeat your password" />
                    <p class="text-danger">{{ errorMessagePasswordRepeat }}</p>
                  </div>
                  <!-- dateofbirth -->
                  <div class="row">
                    <div class="col-md-3 mb-4">
                      <p>Date of birth:</p>
                    </div>
                    <div class="col mb-4">
                      <div class="form-outline mb-6">
                        <input v-model="dateOfBirthBox" type="date" class="form-control" id="dateOfBirthBox" placeholder="Date of birth" />
                      </div>
                    </div>
                  </div>
                  <!-- dateofbirth -->
                  <div class="d-flex justify-content-center">
                    <button @click.prevent="signUpCheck" class="btn btn-primary btn-block btn-lg buttonColor text-body">Sign up</button>
                  </div>
                  <p class="text-center text-muted mt-4 mb-0">Already have an account?
                    <router-link class="fw-bold text-body signuplink" to="/login">Login here</router-link>
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

export default {
  name: 'SignUpView',
  data () {
    return {
      errorMessageName: '',
      errorMessagePassword: '',
      errorMessagePasswordRepeat: '',
      firstName: '',
      lastName: '',
      emailBox: '',
      educationBox: '',
      postCodeBox: '',
      userName: '',
      password: '',
      repeatPassword: '',
      dateOfBirthBox: ''
    }
  },

  methods: {
    signUp () {
      // a methode to put the inputs in the database (later on)
      router.push({ name: 'login' })
    },
    // this methode uses a regex that looks if the password matches the regex.
    passwordChecker (password) {
      const regexPasword = /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,255}$/
      if (password.match(regexPasword)) {
        return true
      } else {
        return false
      }
    },
    // This methode checks the input and validates it
    signUpCheck () {
      let countError = 0
      if (this.userName === '') {
        this.errorMessageName = 'Enter a valid username'
        countError++
      } else {
        this.errorMessageName = ''
      }
      if (this.password === '') {
        this.errorMessagePassword = 'Enter a valid password'
        countError++
      } else if (!this.passwordChecker(this.password)) {
        this.errorMessagePassword = 'Password must consist of at least 6 characters, 1 capital letter, ' +
          ' 1 lowercase letter and 1 number.'
        countError++
      } else {
        this.errorMessagePassword = ''
      }
      if (this.password === '' || this.password !== this.repeatPassword) {
        this.errorMessagePasswordRepeat = 'Password does not match'
        countError++
      } else {
        this.errorMessagePasswordRepeat = ''
      }
      if (countError === 0) {
        this.signUp()
      }
    }
  }
}
</script>

<style>
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
</style>
