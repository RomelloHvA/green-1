<template>
  <div class="main-container">
    <!--    our purpose info row-->
    <div class="row d-flex justify-content-center gap-2 w-80 m-auto">
      <div class="col-lg-6 col-12 m-auto justify-content-center welcomeDiv align-items-lg-start slide-in-animation">
        <div v-show="showItemSequence[0]" class="slide-in-animation">
          <h1 class="text-center text-dark my-2 headerText">{{ contentData.welcomeTitle }}</h1>
<!--          <p class="paragraphText">-->
<!--            Unlock your potential for global impact! On this website, you can explore and act on the UN’s Sustainable-->
<!--            Development Goals tailored to your field of expertise. Ready to make a difference? Take our quiz now!-->
<!--          </p>-->
          <p class="paragraphText">{{ contentData.welcomeText }}</p>
          <button class="btn btn-primary quiz-button" @click="goToQuiz">To Quiz!</button>
      </div>
      </div>
      <div class="col-lg-6 col-12 purpose-card">
        <div v-show="showItemSequence[1]" class="slide-in-animation card cardSpecific">
          <div class="card-body">
            <h1 class="card-title">{{ contentData.purposeTitle }}</h1>
            <p class="card-text">{{ contentData.purposeText }}</p>
          </div>
        </div>
      </div>
      <!--      quiz button row-->
      <div class="row d-flex justify-content-center">
      </div>
      <!--      SDG overview field rows -->
        <div  v-show="showItemSequence[2]" class="row gy-3 slide-in-animation"><h2 class="headerText2">{{ contentData.SdgInfoTitle }}</h2></div>
        <div class="d-flex m-auto justify-content-center">
        <div v-show="!showItemSequence[2]" class="spaceForAnimation"></div>
        <SdgOverview :showItem="showItemSequence[2]" />
      </div>
    </div>
  </div>
</template>

<script>

import SdgOverview from '@/components/LandingPage/SdgOverview.vue'
import { inject } from 'vue'

/**
 * LandingPage Component
 * This is the first page a user will see. Template consists of information about
 * our purpose, a quiz button and a Sustainable Development Goal overview
 */
export default {
  name: 'LandingPage',
  inject: ['contentService'],
  components: { SdgOverview },
  data () {
    return {
      itemShowIndex: 0,
      textIndex: 0,
      showItemSequence: [false, false, false, false],
      // content data
      contentData: {
        welcomeTitle: '',
        welcomeText: '',
        purposeTitle: '',
        purposeText: '',
        SdgInfoTitle: ''
      }
    }
  },
  mounted () {
    /**
     * This is a function that will be called every 700ms and will show the next item in the showItemSequence array
     * This is used to show the welcome text in a sequence
     * @author Marco de Boer
     */
    setInterval(() => {
      if (this.showItemSequence.length === this.textIndex) {
        clearInterval()
        return
      }
      this.showItemSequence[this.textIndex] = true
      this.textIndex++
    }, 500)
  },
  methods: {
    /**
     * Redirects user to quiz page
     */
    goToQuiz () {
      this.$router.push({ path: '/quiz' })
      this.scrollToTop()
    },
    /**
     * Redirects to About Us page
     */
    goAboutUs () {
      this.$router.push({ path: '/about_us' })
      this.scrollToTop()
    },
    /**
     * Scroll user to top
     */
    scrollToTop () {
      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      })
    },
    /**
     * Using the content adaptor, find all content data for a given page
     * @returns {Promise<void>} an object with content data retrieved from the database
     */
    async loadContent () {
      const contentService = inject('contentService')
      const APIResults = await contentService.findContentByPageId(1)
      this.setContent(APIResults)
    },
    /**
     * Fill content fields with the correct data
     * @param data object that holds content data for given page
     */
    setContent (data) {
      let counter = 0
      try {
        for (const content in this.contentData) {
          switch (sessionStorage.getItem('language')) {
            case 'en-US':
              this.contentData[content] = data.editableContent.value[counter].contentEnglish
              break
            case 'nl-NL':
              this.contentData[content] = data.editableContent.value[counter].contentDutch
              break
          }
          counter++
        }
      } catch (err) {
        alert('No connection to database...')
      }
    }
  },
  beforeMount () {
    // load content data from db
    this.loadContent()
  }
}
</script>

<style>
.spaceForAnimation {
  height: 800px;
}
.card-title {
  font-weight: bold;
}
.welcomeDiv {
  margin-top: 100px !important;
  margin-left: 0 !important;
}

.headerText {
  font-size: 2.8rem;
  font-weight: bold;
  text-align: start !important;
  margin-top: 50px;
  margin-bottom: 50px;
  color: #292b2e;
}

.headerText2 {
  font-size: 2.8rem;
  font-weight: bold;
  text-align: center !important;
  margin-top: 50px;
  color: #292b2e;
}

.paragraphText {
  font-size: 1.2rem;
  text-align: justify;
  text-justify: inter-word;
}
.w-80 {
  width: 90%;
}

.main-container {
  overflow-x: hidden;
  /*background-image: linear-gradient(#401B96, #ddf3f3 3%, #ddf3f3 97%, #401B96);*/
  background-color: #ffffff;
  padding-bottom: 75px;
}

.about-us-text {
  cursor: pointer;
  color: #401B96;
  text-decoration: none;
}

.about-us-text:hover {
  text-decoration: underline;
  color: #411C97;
}

.card-text {
  text-align: justify;
  text-justify: inter-word;
  /* color: white !important */
}

.cardSpecific {
  background-color: #E0DBF1;
  border: none;
  margin-top: 150px !important;
  border-radius: 10px;
}

.purpose-card {
  margin-top: 25px;
  max-width: 450px;
  font-size: 18px;
}

.fadeAnimation {
  animation: fade-up 2s;
}

.quiz-button {
  width: 200px;
  margin-top: 50px;
  font-size: 1.6rem;
}

.slide-in-animation {
  /* Initial position -50px off the top of its original position */
  transform: translateY(50px);
  opacity: 0; /* Initial opacity set to 0 to make it invisible */

  /* Animation configuration */
  animation: slideIn 1.5s ease-out forwards; /* Name, duration, easing function, fill mode */
}

@keyframes fade-up {
  0% {
    opacity: 0;
    transform: translateY(30px) scale(0.9);
  }
  100% {
    opacity: 1;
    transform: translateY(0px) scale(1);
  }
}

.slide-in-animation {
  /* Initial position -50px off the top of its original position */
  transform: translateY(50px);
  opacity: 0; /* Initial opacity set to 0 to make it invisible */

  /* Animation configuration */
  animation: slideIn 0.8s ease-out forwards; /* Name, duration, easing function, fill mode */
}

@keyframes slideIn {
  to {
    transform: translateY(0); /* Target position */
    opacity: 1; /* Make it fully visible */
  }
}

@media only screen and (max-width: 1000px) {
  .headerText {
    text-align: center !important;
  }
}
</style>
