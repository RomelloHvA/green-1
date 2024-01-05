<template>
  <nav-bar/>
  <router-view/>
  <FooterComponent/>
</template>

<style>

</style>
<script>
import NavBar from '@/components/NavBar/NavBar'
import FooterComponent from '@/components/FooterComponent'
import { RESTAdaptorWithFetch } from '@/services/RESTAdaptorWithFetch'
import Quiz from '@/models/Quiz'
import YesNoQuestion from '@/models/YesNoQuestion'
import MultipleChoiceQuestion from '@/models/MultipleChoiceQuestion'
import { RESTContentAdaptor } from '@/services/RESTContentAdaptor'
import { UsersAdaptor } from '@/services/users-adaptor'
// import CONFIG from '@/services/app-config.js'
import CONFIG from '@/app-config'
import { ProfilesAdaptor } from '@/services/ProfilesAdaptor'
import { GoalsAdaptor } from '@/services/GoalsAdaptor'
import { ActionPlansAdaptor } from '@/services/ActionPlansAdaptor'
import Sector from '@/models/Sector'
import { RESTImageAdaptor } from '@/services/RESTImageAdaptor'
import { SessionSbService } from '@/services/SessionSbService'
import { shallowReactive } from 'vue'
import { FetchInterceptor } from '@/services/FetchInterceptor'

export default {
  components: {
    NavBar,
    FooterComponent
  },
  provide () {
    this.theSessionService = shallowReactive(
      new SessionSbService(CONFIG.BACKEND_URL + '/authentication/login'))
    this.FetchInterceptor =
      new FetchInterceptor(this.theSessionService, this.$router)
    return {
      quizService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/quiz', Quiz.copyBuilderConstructor),
      quizLiveService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/quiz', Quiz.copyConstructor),
      questionTrueFalseService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/question', YesNoQuestion.copyConstructor),
      questionMultipleChoiceService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/question', MultipleChoiceQuestion.copyConstructor),
      sectorService: new RESTAdaptorWithFetch(CONFIG.BACKEND_URL + '/sector', Sector.copyConstructor),
      contentService: new RESTContentAdaptor(CONFIG.BACKEND_URL + '/page'),
      imageService: new RESTImageAdaptor(CONFIG.BACKEND_URL + '/page/image'),
      usersServices: new UsersAdaptor(CONFIG.BACKEND_URL),
      profileService: new ProfilesAdaptor(CONFIG.BACKEND_URL + '/profiles'),
      goalService: new GoalsAdaptor(CONFIG.BACKEND_URL + '/goals'),
      actionPlanService: new ActionPlansAdaptor(CONFIG.BACKEND_URL + '/actionplans'),
      sessionService: this.theSessionService
    }
  },
  unmounted () {
    this.FetchInterceptor.unregister()
  },

  created () {
    // set language to english in sessionStorage, set default language to english (en-US)
    if (sessionStorage.getItem('language') === null) {
      sessionStorage.setItem('language', 'en-US')
    }
    // sessionStorage.setItem('language', 'nl-NL')
  }
}
</script>
