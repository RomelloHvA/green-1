<template>
  <div class="card m-1" style="width: 18rem;">
    <img class="card-img-top" alt="..." :src="imgsrc">
    <div class="card-body">
      <h5 class="card-title">{{sdgData.title}}</h5>
      <p class="card-text">{{cardDescription}}</p>
      <div class="card-footer">
        <flip-button-component @click="changeClicked" :is-clicked="isClicked"/>
      </div>
    </div>
  </div>
</template>

<script>
import FlipButtonComponent from '@/components/quizResultsComponents/flipButtonComponent'
export default {
  name: 'sdgCardComponent',
  components: { FlipButtonComponent },
  data () {
    return {
      isClicked: false
    }
  },
  methods: {
    changeClicked () {
      this.isClicked = !this.isClicked
    }
  },
  props: {
    sdgData: {
      type: Object,
      default: () => ({
        gifSrc: '',
        src: '',
        description: '',
        generalContribution: '',
        title: ''
      })
    }
  },
  computed: {
    imgsrc () {
      return this.isClicked ? this.sdgData.gifSrc : this.sdgData.src
    },
    cardDescription () {
      return this.isClicked ? this.sdgData.generalContribution : this.sdgData.description
    }
  }
}
</script>

<style scoped>

</style>
