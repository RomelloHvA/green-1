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
                        <div class="signup-label">First Name*</div>
                        <input v-model="firstName" @input="validateInput" type="text" class="form-control" id="firstName" placeholder="" />
                        <p class="text-danger">{{ errorFirstName }}</p>
                      </div>
                    </div>
                    <div class="col-md-6 mb-4">
                      <div class="form-outline">
                        <div class="signup-label">Last Name*</div>
                        <input v-model="lastName" @input="validateInput" type="text" class="form-control" id="lastName" placeholder="" />
                        <p class="text-danger">{{ errorLastName }}</p>
                      </div>
                    </div>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label">Email*</div>
                    <input v-model="email" @input="validateInput" type="email" class="form-control" id="emailBox" placeholder="" />
                    <p class="text-danger">{{ errorEmail }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label">HvA Faculty</div>
                    <select name="sectors" id="sectors" class="form-control">
                      <option class="form-control" v-for="faculty in this.faculties" :key="faculty" :value="faculty"
                      >{{faculty}}</option>
                      <option class="form-control" selected>I'm not sure</option>
                    </select>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label">Postal Code*</div>
                    <input v-model="postCode" @input="validateInput" type="text" class="form-control" id="postCodeBox" placeholder="" />
                    <p class="text-danger">{{ errorPostalCode }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label">Username*</div>
                    <input v-model="userName" @input="validateInput" type="text" class="form-control" id="usernameBox" placeholder="" />
                    <p class="text-danger">{{ errorUsername }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label">Password*</div>
                    <input v-model="password" @input="validateInput" type="password" class="form-control" id="passwordBox" placeholder="" />
                    <p class="text-danger">{{ errorPassword }}</p>
                  </div>
                  <div class="form-outline mb-4">
                    <div class="signup-label"></div>
                    <div class="signup-label">Confirm Password*</div>
                    <input v-model="confirmPassword" @input="validateInput" type="password" class="form-control" id="confirmPasswordBox" placeholder="" />
                    <p class="text-danger">{{ errorConfirmPassword }}</p>
                  </div>
                  <!-- dateofbirth -->
                  <div class="row">
                    <div class="col mb-4">
                      <div class="form-outline mb-6">
                        <div class="signup-label">Date of Birth (optional)</div>
                        <input v-model="dateOfBirth" @input="validateInput" type="date" class="form-control" id="dateOfBirthBox" placeholder="" />
                        <p class="text-danger">{{ errorDateOfBirth }}</p>
                      </div>
                    </div>
                  </div>
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
/* eslint-disable */
import router from '@/router'
import { useToast } from 'vue-toast-notification'

export default {
  name: 'SignUpValidationView',
  inject: ['usersServices'],
  data () {
    return {
      errorFirstName: '',
      errorLastName: '',
      errorEmail: '',
      errorFaculty: '',
      errorPostalCode: '',
      errorUsername: '',
      errorPassword: '',
      errorConfirmPassword: '',
      errorDateOfBirth: '',
      firstName: null,
      lastName: '',
      email: '',
      faculty: '',
      postCode: '',
      userName: '',
      password: '',
      confirmPassword: '',
      dateOfBirth: null,
      faculties: ['Applied Social Sciences and Law', 'Business and Economics',
        'Digital Media and Creative Industries', 'Education', 'Health', 'Sports and Nutrition',
        'Technology'] // HvA faculties src:https://www.amsterdamuas.com/about-auas/organisation/faculties/faculties.html?_ga=2.137166840.975923899.1700941795-516242571.1663835270
    }
  },

  methods: {
    /**
     * Function validates an event. Checks which field it has to validate
     * automatically warns the user when they have input an invalid value
     * @param event @input event
     */
    validateInput: function (event) {
      let message = ''
      // regex for different fields
      const nameRegex = '^[a-zA-Z\\-]+$' // checks for only letters
      const emailRegex = '^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$' // checks for email format
      const postalRegex = '^\\d{4}[a-zA-Z]{2}$' // checks postal code format (1234AB/1234ab)
      const usernameRegex = '^[a-zA-Z0-9._-]{6,12}$'// checks for only letters, numbers and char.: ._-
      const passwordRegex = '^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!#$@%&*]).{8,}$'
      // magic number variables
      const minNameLength = 2
      const maxNameLength = 20
      const minPasswordLength = 8
      const maxPasswordLength = 20
      // checks for password of with minimal length 8,
      // 1 lowercase, 1 uppercase, 1 spec. char.: !#$%&*@
      let putError = false
      switch (event.target.id) {
        case 'firstName':
          // do not interact if field is empty
          if (event.target.value.length === 0) {
            break
          }
          // interact when regex match is not found
          if (!event.target.value.match(nameRegex)) {
            message = 'Name must consist out of letters only'
            putError = true
          } else if (event.target.value.length < minNameLength || event.target.value.length > maxNameLength) {
            message = 'Name length must be 2 or more'
            putError = true
          }
          break
        case 'lastName':
          // do not interact if field is empty
          if (event.target.value.length === 0) {
            break
          }
          // interact when regex match is not found
          if (!event.target.value.match(nameRegex)) {
            message = 'Name must consist out of letters only'
            putError = true
          } else if (event.target.value.length < minNameLength || event.target.value.length > maxNameLength) {
            message = 'Name length must be 2 or more'
            putError = true
          }
          break
        case 'emailBox':
          if (!event.target.value.match(emailRegex)) {
            putError = true
          }
          break
        case 'postCodeBox':
          if (!event.target.value.match(postalRegex)) {
            putError = true
          }
          break
        case 'usernameBox':
          if (!event.target.value.match(usernameRegex)) {
            putError = true
          }
          break
        case 'passwordBox':
          if (event.target.value.length < minPasswordLength) {
            message = 'Password is too short'
            putError = true
          } else if (event.target.value.length > maxPasswordLength) {
            message = 'Password is too long'
            putError = true
          } else if (!event.target.value.match(passwordRegex)) {
            message = 'Password requires 1 lowercase, 1 uppercase, 1 number & 1 special character (!#$%&*@)'
            putError = true
          }
          break
        case 'confirmPasswordBox':
          if (event.target.value !== this.password) {
            putError = true
          }
          break
        case 'dateOfBirthBox':
          if (!this.verifyDate(new Date(event.target.value))) {
            putError = true
          }
          break
      }
      // console.log(event.target.id, message)
      this.setErrorMessage(event.target.id, putError, message)
    },
    /**
     * Function sets the content of error message properties which will
     * then be displayed on the user's screen
     * @param id of input field
     * @param putError boolean determines if text should be added or removed
     * @param message error message to be displayed (optional)
     */
    setErrorMessage (id, putError, message) {
      // find correct id
      switch (id) {
        case 'firstName':
          this.errorFirstName = putError ? message : ''
          break
        case 'lastName':
          this.errorLastName = putError ? message : ''
          break
        case 'emailBox':
          this.errorEmail = putError ? 'Enter a valid email address' : ''
          break
        case 'postCodeBox':
          this.errorPostalCode = putError ? 'Enter a valid postal code' : ''
          break
        case 'usernameBox':
          this.errorUsername = putError ? 'Enter a valid username (length: 6-12, - , _ , . )' : ''
          break
        case 'passwordBox':
          this.errorPassword = putError ? message : ''
          break
        case 'confirmPasswordBox':
          this.errorConfirmPassword = putError ? 'Passwords must match' : ''
          break
        case 'dateOfBirthBox':
          this.errorDateOfBirth = putError ? 'You must be older than 16' : ''
      }
    },
    /**
     * Verifies a given date
     * @param date Date object
     * @returns {boolean} true if the input date is 'older' than 16 years.
     */
    verifyDate (date) {
      const minYear = 16
      // construct a full date
      const today = new Date()
      return (today.getFullYear() - date.getFullYear()) >= minYear
    },
    async signUp() {
      // Start by generating a Json file for the user
      const userJson = this.createUserJson()
      try {
        // Send a request to the backend with the Json file
        const user = await this.usersServices.asyncSave(userJson)
        if (!user) {
          // Displaying the error
          useToast().error(user.responseText)
        } else {
          // Displaying success message
          useToast().success("Sign Up Successful! You will be sent to the login page in a moment...")
          // Wait 5 seconds and send the user to the login page
          await new Promise(resolve => setTimeout(resolve, 5000));
          router.push({ name: 'login' })
        }
      } catch (error) {
        console.log(error)
      }
    },
    createUserJson () {
      // user json requires:
      // [user_id, sector_id, first_name, last_name, email, security_clearance,
      //  password, username, bio (null), occupation (null), date_of_birth, postal_code, user_col (null) ]
      const newUser = {
        sector_id: this.faculties.indexOf(this.faculty) + 1,
        first_name: this.firstName,
        last_name: this.lastName,
        email: this.email,
        security_clearance: 1,
        password: this.password,
        username: this.userName,
        postal_code: this.postCode,
        date_of_birth: this.dateOfBirth
      }
      return newUser
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
      } else if (!this.password.match(/^(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,255}$/)) {
        this.errorMessagePassword = 'Password must consist of at least 6 characters, 1 capital letter, ' +
          ' 1 lowercase letter and 1 number.'
        countError++
      } else {
        this.errorMessagePassword = ''
      }
      if (this.password === '' || this.password !== this.confirmPassword) {
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

<style scoped>

.text-danger {
  text-align: left;
  font-size: 10px !important;
}
.form-control {
  text-align: left;
  border-radius: 0 5px 5px 5px;
}
.form-outline  {
  text-align: left;
}

.signup-label {
  display: flex;
  justify-content: start;
  font-size: 12px;
}

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
