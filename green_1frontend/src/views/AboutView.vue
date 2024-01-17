<template>
  <div class="about">
    <HeaderComponent/>
    <AboutNavbar/>
    <router-view/>
  </div>
</template>
<script>
import HeaderComponent from '@/components/AboutUs/HeaderComponent'
import AboutNavbar from '@/components/AboutUs/AboutNavbar'
export default {
  components: { AboutNavbar, HeaderComponent }
}
</script>
