<template>
  <li class="nav-item dropdown">
    <a class="nav-link dropdown-toggle" role="button" data-bs-toggle="dropdown" aria-expanded="false">
      Admin dashboard
    </a>
    <ul class="dropdown-menu">
      <li><a class="dropdown-item" href="#">User Statistics</a></li>
      <li><a class="dropdown-item" href="#">SDG Statistics</a></li>
      <li>
        <hr class="dropdown-divider">
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
