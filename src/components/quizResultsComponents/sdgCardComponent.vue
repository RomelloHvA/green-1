<template>
  <div class="card child">
    <img class="card-img-top" alt="..." :src="imgsrc">
    <div :style="{ backgroundColor: sdgData.color, height: bottomHeight }"  class="card-body bottompart p-0">
      <div v-if="!isFromQuizResults">
        <h5 class="card-title">{{sdgData.title}}</h5>
        <p class="card-text">{{cardDescription}}</p>
        <div class="card-footer">
          <flip-button-component @click="changeClicked" :is-clicked="isClicked"/>
        </div>
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
      isClicked: false,
      isMounted: false,
      isImageLoaded: false,
      mouseOver: false
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
        title: '',
        color: ''
      })
    },
    maxHeight: {
      type: Number
    },
    isFromQuizResults: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    this.isMounted = true
    this.$nextTick(() => {
      const img = document.querySelector('.card-img-top')
      if (img && img.complete) {
        this.isImageLoaded = true
      } else {
        img.addEventListener('load', () => {
          this.isImageLoaded = true
        })
      }
    })
  },
  computed: {
    imgsrc () {
      return this.isClicked ? this.sdgData.gifSrc : this.sdgData.src
    },
    cardDescription () {
      return this.isClicked ? this.sdgData.generalContribution : this.sdgData.description
    },
    bottomHeight () {
      if (this.isMounted && this.isImageLoaded) {
        const imgHeight = document.querySelector('.card-img-top').offsetHeight
        if (imgHeight > 0) {
          if (this.sdgData.score === undefined || (this.sdgData.highestScore !== this.sdgData.score && this.sdgData.score === 1)) {
            return '0px'
          }
          return this.sdgData.score / this.sdgData.highestScore * this.maxHeight - imgHeight + 'px'
        }
      }
      return '0px'
    }
  }
}
</script>

<style scoped>
.child {
  position: absolute;
  max-height: 100%;
  bottom: 0;
}

.bottompart {
  transition: height 2s;
  overflow: hidden;
  display: flex !important;
  flex-direction: column;
  justify-content: flex-end;
  align-items: center;
  max-height: 100%;
}

.card-img-top {
  transition: transform 1s;
  max-height: 75%;
  object-fit: cover;
  max-width: 120px;
}

.card {
  transition: transform 1s;
  max-height: 100%;
  max-width: 100%;
  object-fit: cover;
  border-radius: 0;
  border: none;
  box-shadow: none;
  min-height: 0px !important;
}

@media screen and (max-width: 574px) {
  .card-img-top {
    max-height: 100%;
    max-width: 100%;
  }
  .card {
    max-height: 100%;
    max-width: 100%;
  }
}
</style>
