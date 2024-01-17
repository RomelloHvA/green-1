<template>
  <div class="col-lg-4">
    <div class="card bg-light m-md-1 mx-sm-0" style="max-width: 540px;">
      <div class="row no-gutters">
        <div class="col-md-4">
          <img :src=src class="card-img" alt="..." style="max-width: 155px">
        </div>
        <div class="col-md-8">
          <div class="card-body">
            <h5 class="card-title">{{ title }}</h5>
            <span class="card-text">{{ cardDescription }}</span>
            <!--          Button to show more info-->
            <div class="card-footer">
              <AdminPanelButtonComponent @click="changeShowDescription" :text="buttonText"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AdminPanelButtonComponent from '@/components/AdminDashboard/PanelComponent/AdminPanelButtonComponent'

export default {
  name: 'AdminPanelComponent',
  components: { AdminPanelButtonComponent },
  props: ['title', 'adminDescription', 'src'],
  data () {
    return {
      showDescription: false
    }
  },
  methods: {
    changeShowDescription () {
      this.showDescription = !this.showDescription
    }
  },
  computed: {
    cardDescription () {
      return this.showDescription ? this.adminDescription : ''
    },
    buttonText () {
      return this.showDescription ? 'Hide info' : 'Learn more'
    }
  }
}
</script>

<style scoped>

</style>
