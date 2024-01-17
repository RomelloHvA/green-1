<template>
    <div class="container justify-content-center d-flex m-auto bigLoadingSpinner">
        <!-- <h5 class=" mx-3">{{message}}</h5> -->
        <div class="spinner-border primary-color" role="status">
            <span class="sr-only"></span>
        </div>
    </div>
</template>

<script>
export default {
  name: 'LoadingComponent',
  data () {
    return {
      message: 'Loading '
    }
  }
}
</script>

<style>
.bigLoadingSpinner .spinner-border {
  margin-top: 50px;
  margin-bottom: 50px;
  width: 5rem;
  height: 5rem;
  border-width: 0.5rem;
}
</style>
