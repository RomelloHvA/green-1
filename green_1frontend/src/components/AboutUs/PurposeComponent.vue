<template>
  <h1 class="text-center headerText1">{{ contentData.purposeTitle }}</h1>
  <div class="card-body justify-content-center d-flex flex-row gap-5" id="body">
    <div class="card2">
      <img alt="purpose" class="team card-img align-self-center" src='../../assets/img/about-us/target.jpg' id="purpose-component">
      <p class="text-center">{{ contentData.purposeText }}</p>
    </div>
  </div>
</template>

<script>
import { inject } from 'vue'

/**
 * This is the component that is responsible for showing the purpose of our web-page
 */
export default {
  name: 'PurposeComponent',
  data () {
    return {
      contentData: {
        purposeTitle: '',
        purposeText: ''
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
      const APIResults = await contentService.findContentByPageId(4)
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

h1.text-center {
  font-family: fantasy;
  margin-top: 2%;
  animation: fade-up 1s;
}
.card2 {
  background-color: #E0DBF1;
  max-width: 80%;
  height: auto;
  border-radius: 10px;
  padding: 10px;
}

#purpose-component {
  padding: 15px;
  max-width: 30vw;
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
  #purpose-component {
    display: none;
  }
}
</style>
