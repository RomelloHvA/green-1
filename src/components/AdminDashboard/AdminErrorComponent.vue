<template>
<div class="container">
  <h3 class="text-danger-emphasis">{{ error }} </h3>
</div>
</template>

<script>
export default {
  name: 'AdminErrorComponent',
  props: {
    error: null
  }
}
</script>

<style scoped>

</style>
