<template>
  <li class="nav-item dropdown">
    <router-link class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false" to="/admin_dashboard">
      Admin dashboard
    </router-link>
    <ul class="dropdown-menu">
      <li><router-link class="dropdown-item" to='/admin_dashboard'>Explanation</router-link></li>
      <hr class="dropdown-divider">
      <li><a class="dropdown-item" href="#">SDG Statistics</a></li>
      <li>
      </li>
      <li><a class="dropdown-item" href="#">Something else here</a></li>
    </ul>
  </li>
</template>
<script>
export default {
  name: 'NavBarDropdown'
}
</script>
