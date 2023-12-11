<template>
  <img class="rotating-image" src="@/assets/img/logos/SDG_Wheel_Transparent_WEB.png">
</template>

<script>
export default {
  name: 'AdminLoaderComponent'
}
</script>

<style scoped>
@keyframes rotate {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
/* Apply the animation to the image */
.rotating-image {
  animation: rotate 5s linear infinite; /* Adjust the duration or animation properties as needed */
  max-width: 10%;
  background-color: transparent;
}
</style>
