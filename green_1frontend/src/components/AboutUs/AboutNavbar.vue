<template>
<!--  This is the navbar that only displays on the about us page-->
  <nav class="navbar navbar-dark navbar-expand-lg bg-navbar-color">
    <div class="container-fluid">
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse d-flex justify-content-center" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link to="client" class="nav-link active" aria-current="page" href="#">Our Client</router-link>
          </li>
          <li class="nav-item">
            <router-link to="team" class="nav-link" href="#">Our Team</router-link>
          </li>
          <li class="nav-item">
            <router-link to="purpose" class="nav-link" href="#">Our Purpose</router-link>
          </li>
        </ul>
      </div>
    </div>
  </nav>
</template>

<script>
/**
 * This component is only used for navigating around in the about-us page
 */
export default {
  name: 'AboutNavbar'
}
</script>

<style scoped>
.bg-navbar-color {
  background: #401B96;
}

.container-fluid {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}

</style>
