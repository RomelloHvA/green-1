<template>
  <p class="fw-bold fs-4 text-decoration-underline">Page preview for Landing page:</p>
  <div class="border-black">
    <LandingPagePreview v-if="pageId === 1"
    :pageId="pageId" :imageClone="imageClone"/>
  </div>
</template>

<script>
import LandingPagePreview from '@/components/AdminDashboard/AdminImageEdit/LandingPagePreview'

export default {
  name: 'PagePreview',
  components: { LandingPagePreview },
  props: {
    pageId: Number,
    imageClone: Object
  }
}
</script>

<style scoped>
.border-black {
  border: 5px solid #401B96;
}
</style>
