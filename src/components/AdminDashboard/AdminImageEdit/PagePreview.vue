<template>
<p>Page preview</p>
  <p>Page id = {{ $props.pageId }}</p>
</template>

<script>
import { ref } from 'vue'

export default {
  name: 'PagePreview',
  props: {
    pageId: Number
  },
  setup (props) {
    const pageId = ref(props.pageId)
    console.log('pageId: ' + pageId.value)
    return {}
  }
}
</script>

<style scoped>

</style>
