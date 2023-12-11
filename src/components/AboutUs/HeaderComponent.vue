<template>
  <div class="header-body">
    <img alt="header" class="header opacity-80" src='../../assets/img/about-us/about-us.png' id="header-component">
    <h1 class="card-text" id="header-text">About Us</h1>
  </div>
</template>

<script>
/**
 * This is the header component which is used as a banner for the about-us page
 */
export default {
  name: 'HeaderComponent'
}
</script>

<style scoped>
.header {
  width: 100%;
  height: auto;
  animation: fade-down 0.5s;
}

.header-body {
  position: relative;
  animation: fade-down 0.5s;
}
#header-text {
  font-family: fantasy;
  font-size: 6rem;
  color: white;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  border: 6px solid white;
  padding: 6px 14px;
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
@media screen and (max-width: 1100px) {
  #header-text {
    display: none;
  }
}
</style>
