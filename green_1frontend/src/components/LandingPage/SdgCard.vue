<template>
  <div class="col sdg-card d-flex" v-on:mouseover="mouseover" v-on:mouseleave="mouseleave">
    <img :src="currentContent" class="img-fluid" :alt="'SDG-' + this.content.id">
    <div class="ml-auto" v-if="hover">
      <button @click="goPage" class="extra-sdg-info-btn"
              :style="{'background-color': this.content.color, 'border-color': this.content.color}"
              @mouseenter="mouseHover = true" @mouseleave="mouseHover = false">
      <svg v-if="!mouseHover" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-info-circle" viewBox="0 0 16 16">
        <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
        <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
      </svg>
      <svg v-if="mouseHover" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="white" class="bi bi-info-circle-fill" viewBox="0 0 16 16">
        <path d="M8 16A8 8 0 1 0 8 0a8 8 0 0 0 0 16zm.93-9.412-1 4.705c-.07.34.029.533.304.533.194 0 .487-.07.686-.246l-.088.416c-.287.346-.92.598-1.465.598-.703 0-1.002-.422-.808-1.319l.738-3.468c.064-.293.006-.399-.287-.47l-.451-.081.082-.381 2.29-.287zM8 5.5a1 1 0 1 1 0-2 1 1 0 0 1 0 2z"/>
      </svg>
    </button></div>
  </div>
</template>

<script>
/**
 * SdgCard Component
 * Holds the HTML template of a SDG goal card with additional info
 */
export default {
  name: 'SdgCard',
  props: ['content'],
  data () {
    return {
      hover: false,
      currentContent: this.content.image,
      mouseHover: false
    }
  },
  methods: {
    /**
     * Function changes content of an image when hovered
     */
    mouseover: function () {
      this.hover = true
      this.currentContent = this.content.action
    },
    /**
     * Function changes content of an images when no longer hovered
     */
    mouseleave: function () {
      this.hover = false
      this.currentContent = this.content.image
    },
    /**
     * Function redirects to a page with goal information
     */
    goPage () {
      this.$router.push({ path: '/sdg/' + this.content.id })
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
    }
  }
}
</script>

<style>
.extra-sdg-info-btn {
  display: flex;
  align-items: center;
  left: 7px;
  /*height: 11%;*/
  height: fit-content; /* fit content inside button */
  border-width: 2px;
  border-radius: 0 10px 10px 0; /* top and bottom right corners are round */
  width: auto;
  /*font-size: 12px;*/
  font-size: 85%;
  text-decoration: none !important;
}

.sdg-card {
  object-fit: cover !important;
  transition: all 250ms ease-in-out;
}

.sdg-card:hover {
  transform: scale(1.85); /* Enlarged hovered image */
  min-width: 8vw; /* set min width for readability */
  height: auto;
}
</style>
