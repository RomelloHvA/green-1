<template>
  <li class="nav-item ">
    <router-link class="nav-link" exact-active-class="navbar-active" :to='route'>{{ itemText }}</router-link>
  </li>
</template>
<script>
export default {
  name: 'NavBarItem',
  props: ['itemText', 'route']
}
</script>
<style>
.navbar-active {
  text-decoration: underline;
}

.nav-link{
  color: white !important;
}

.nav-item {
  color: white !important;
}
</style>
