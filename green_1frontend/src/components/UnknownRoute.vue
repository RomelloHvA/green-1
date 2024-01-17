<template>
  <img class="img-fluid" src="../assets/img/error/404.gif" alt="not-found" id="404">
  <h1 class="text-center">Sorry, the page that you were looking for could not be found...</h1>
  <h4 class="text-center">Please make sure the website URL is correct, or try to contact us with the information at the bottom of the page!</h4>
  <button class="btn btn-primary" @click="returnToHome">Or just go back to home</button>
</template>
<script>
/**
 * This is the component that is responsible for displaying the 'path not found' error message when the path of the URL is invalid
 */
export default {
  name: 'UnknownRoute',
  data () {
    return {
    }
  },
  methods: {
    returnToHome () {
      this.$router.push({ path: '/' })
    }
  }
}
</script>

<style scoped>
img {
  max-width: 80vw;
  height: auto;
  animation: fade-down 1s;
}
.text-center {
  animation: fade-up 1s;
}
.btn-primary {
  margin-bottom: 5%;
  animation: fade-up 1s;
}
@keyframes fade-down {
  0% {
    opacity: 0;
    transform: translateY(-30px) scale(0.9);
  }
  100% {
    opacity: 1;
    transform: translateY(0px) scale(1);
  }
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
</style>
