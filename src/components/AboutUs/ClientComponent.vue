<template>
  <h1 class="text-center headerText1"> {{ contentData.headerTitle }}</h1>
  <div class="card-body justify-content-center d-flex flex-row gap-5" id="body">
    <div class="card2">
      <img alt="client" class="team card-img align-self-center" src='../../assets/img/about-us/client-logo.png' id="client-component">
      <p class="text-center">{{ contentData.clientIntroduction }}</p>
      <hr>
      <p class="text-center font-monospace">{{ contentData.clientInformation }}</p>
      <hr>
      <a href="https://sdgs.un.org/about" target="_blank" class="about-us-text">Find out more about the Sustainable Development Goals...</a>
    </div>
  </div>
</template>

<script>
import { inject } from 'vue'
/**
 * This is the component that is responsible for displaying the information about our client
 */
export default {
  name: 'ClientComponent',
  data () {
    return {
      contentData: {
        headerTitle: '',
        clientIntroduction: '',
        clientInformation: ''
      }
    }
  },
  methods: {
    /**
     * Using the content adaptor, find all content data for a given page
     * @returns {Promise<void>} an object with content data retrieved from the database
     */
    async loadContent () {
      const contentService = inject('contentService')
      const APIResults = await contentService.findContentByPageId(2)
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
  /**
   * When created, load all content data for the webpage
   */
  beforeMount () {
    // load content from db
    this.loadContent()
  }
}
</script>

<style scoped>
#body {
  padding: 25px;
  animation: fade-up 3s;
}

.headerText1{
  font-size: 2.8rem;
  font-weight: bold;
  margin-top: 50px;
  color: #292b2e;
}

h1.text-center {
  font-family: fantasy;
  color: rgb(37, 37, 37);
  margin-top: 2%;
  animation: fade-up 1s;
}

.textWhite {
  color: white !important;
}

.card2 {
  background-color: #E0DBF1;
  max-width: 80%;
  height: auto;
  border-radius: 10px;
}

#client-component {
  padding: 15px;
  max-width: 50vw;
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

@media screen and (max-width: 1100px) {
  #client-component {
    display: none;
  }
}
</style>
