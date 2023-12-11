<template>
<!--  This is the whole footer -->
  <div class="card card-body text-center removeBorderRadius" id="footerBody">
    <div class="card-body m-auto w-100">
<!--      <h5 class="card-title">Looks like you have reached the end...</h5>-->
      <div class="card-container">
        <!--  This is the section for potential questions of the users -->
        <div class="card" id="footerBox">
          <h5 class="card-title" id="footerTitle">Questions</h5>
          <hr/>
          <p class="card-text">Get started</p>
          <p class="card-text">How to use</p>
          <p class="card-text">Privacy Policy</p>
          <p class="card-text">Terms of Use</p>
          <p class="card-text">Content Usage Guidelines</p>
        </div>
        <!--  This is the section where the user can find ways to contact us -->
        <div class="card" id="footerBox">
          <h5 class="card-title" id="footerTitle">Contact</h5>
          <hr/>
          <p class="card-text">Hotline: 020 12345678</p>
          <p class="card-text">Email us at: greenoffice01@gmail.com</p>
          <p class="card-text">YouTube</p>
          <p class="card-text">Instagram</p>
          <p class="card-text">LinkedIn</p>
        </div>
        <!--  This is the section where the user can find more information about us -->
        <div class="card" id="footerBox">
          <h5 class="card-title" id="footerTitle">About Us</h5>
          <hr/>
          <a href="https://sdgs.un.org/goals" target="_blank" class="link-light">Sustainable Development</a>
          <router-link class="link-light" to="/about_us">Meet the Team</router-link>
        </div>
        <img class="rounded float-end" src="../assets/img/logos/SDG_UN_LOGO.png" alt="sdg-un-logo" id="footerLogo">
      </div>
      <!--  This button allows the user to go back to the top of the page -->
<!--      <p class="card-text">Or just simply go back to the top of this page with the button below</p>-->
      <button class="btn btn-footer" id="scroll-to-top" @click="scrollToTop">Go back to top</button>
      <div class="language-selector">
      <label for="set-language">Language:</label>
        <select id="set-language" name="language" @change="setLanguage($event)">
          <option value="english">English</option>
          <option value="dutch">Nederlands</option>
        </select>
        </div>
    </div>
    <div class="card-footer text-body-secondary">
      <p class="card-text">Presented to you by: HvA HBO-ICT, S206-Green Office Team 1</p>
    </div>
  </div>
</template>

<script>
export default {
  name: 'FooterComponent',
  data () {
    return {
      showScrollButtonButton: false
    }
  },
  /**
   * This method is used for a button which sends the user back to the top of the current page when clicked
   */
  methods: {
    scrollToTop () {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      })
    },
    setLanguage (event) {
      if (event.target.value === 'english') {
        sessionStorage.setItem('language', 'en-US')
      } else if (event.target.value === 'dutch') {
        sessionStorage.setItem('language', 'nl-NL')
      }
    }
  },
  mounted () {
    window.addEventListener('scroll', () => {
      // Show the button when the user scrolls down 300 pixels from the top
      this.showScrollButton = window.scrollY > 300
    })
  }
}
</script>

<style scoped>

.language-selector {
  color: white;
  position: absolute;
  right: 10px;
  bottom: 42px;
}

.card-container {
  margin-top: 20px;
}
.btn-footer {
  background-color: #6D3FD9;
  color:white;
  font-weight: 500;

}
.btn-footer:hover {
  color: #6D3FD9;
  background-color: white;
}
/* Styling for the footer */
.text-center {
  width: 100%;
  margin: 0;
  padding: 0;
  color: red;
}

.removeBorderRadius {
  border-radius: 0 !important;
}

.card-body {
  background-color: #401B96 !important;
}

.card-container{
  display: flex;
  flex-direction: row;
  justify-content: left;
  gap: 2%;
}

#footerBody .card-title, #footerBody .card-text {
  color: white;
  text-align: center;
}

.card-footer {
  background-color: black;
}

#footerBox {
  background-color: #401B85;
  width: 18rem;
  margin-bottom: 2%;
}
#footerBody {
  border: none;
}
#footerTitle {
  margin-top: 5%;
}

#footerLogo {
  margin-left: 5%;
  width: 350px;
  height: 300px;
}

/* Hides the SDG logo when a certain width is reached */
@media screen and (max-width: 1100px) {
  #footerLogo {
    display: none;
  }
}

.btn-primary {
  background-color: white;
  color: black;
  margin-bottom: 0;
}

a{
  margin-bottom: 4%;
}

#scroll-to-top {
  cursor: pointer;
}
</style>
