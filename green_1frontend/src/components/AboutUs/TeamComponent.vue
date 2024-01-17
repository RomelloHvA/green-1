<template>
  <h1 class="text-center"> {{ contentData.teamTitle }}</h1>
  <div class="card-body justify-content-center d-flex gap-5" id="body">
    <div class="card2">
      <img alt="team" class="team card-img" src='../../assets/img/about-us/team.jpg' id="team-component">
      <p class="text-center">{{ contentData.teamInformation }}</p>
    </div>
    <div class="card2">
      <p class="text-center">
        Our members:
      </p>
      <ul class="list-group">
        <li class="list-group-item bg-primaryColor text-white">Romello ten Broeke</li>
        <li class="list-group-item">Marco de Boer</li>
        <li class="list-group-item bg-primaryColor text-white">Justin Chan</li>
        <li class="list-group-item">Santosh Kakkar</li>
        <li class="list-group-item bg-primaryColor text-white">Pim Schuit</li>
        <li class="list-group-item">Jiaming Yan</li>
      </ul>
    </div>
  </div>
</template>
<script>
import { inject } from 'vue'

/**
 * This is the component that is responsible for showing the information about the team
 */
export default {
  name: 'TeamComponent',
  data () {
    return {
      contentData: {
        teamTitle: '',
        teamInformation: ''
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
      const APIResults = await contentService.findContentByPageId(3)
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
  flex-direction: row;
  animation: fade-up 3s;
}
h1.text-center {
  font-family: fantasy;
  margin-top: 2%;
  animation: fade-up 1s;
}

.bg-primaryColor {
  background-color: #401B96;
}

.card2 {
  background-color: #E0DBF1;
  max-width: 80%;
  height: auto;
  border-radius: 10px;
  padding: 10px;
}

#team-component {
  place-self: center;
  padding: 12px;
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
  #team-component {
    display: none;
  }
  #body {
    flex-direction: column;
  }
  .card {
    max-width: 100%;
  }
}
</style>
