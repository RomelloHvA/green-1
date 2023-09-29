<template>
<nav class="navbar navbar-dark navbar-expand-lg bg-navbar-color navbar-item-text ">
  <div class="container-fluid">
    <a class="navbar-brand" href="#"><img class="navbar-logo" src="@/assets/img/logos/hva_logo_transparant.png" alt="HvA Logo"></a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link mx-0 remove-hva-text" href="https://www.hva.nl/">Hogeschool van Amsterdam</a>
        </li>
<!--        NavbarItems-->
        <NavBarItem item-text="Home" route="/"/>
        <NavBarItem item-text="About us" route="/about_us"/>
        <NavBarItem item-text="Quiz" route="/quiz"/>
        <NavBarItem item-text="SDG info" route="/sdg"/>
<!--        Drop down admin dashboard menu -->
        <NavBarDropdown v-if="isAdmin" />
      </ul>
      <button class="btn btn-info" v-if="!isLoggedIn"> Sign up</button>
      <button class="btn btn-success mx-1">Login</button>
    </div>
  </div>
</nav>
</template>
<script>
import NavBarItem from '@/components/NavBar/NavBarItem'
import NavBarDropdown from '@/components/NavBar/NavBarDropdown'

export default {
  name: 'NavBar',
  components: { NavBarDropdown, NavBarItem },
  data () {
    return {
      isAdmin: true,
      isLoggedIn: false
    }
  }
}
</script>
<style scoped>

.bg-navbar-color {
  background: #401B96;
}

.navbar-item-text {
  font-size: 20px;
  text-decoration: none;
}

.navbar-logo {
  max-height: 50px;
  width: auto;
  margin: 0;
  padding: 0;
}

@media screen and (max-width: 1100px) {
  .remove-hva-text {
    display: none;
  }
}

</style>
