<template>
  <li class="nav-item">
    <router-link class="nav-link text-black" active-class="active" :to="route">{{ text }}</router-link>
  </li>
</template>
<script>
export default {
  name: 'AdminNavBarItem',
  props: ['text', 'route']
}
</script>
