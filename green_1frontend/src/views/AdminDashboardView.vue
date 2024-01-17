<template>
  <div class="card text-center">
    <div class="card-header">
<!--      Dashboard navbar-->
      <AdminNavBar/>
    </div>
    <div class="card-body">
<!--      Loads each section of the admin dashboard as clicked in the admin navbar. -->
      <router-view/>
    </div>
  </div>
</template>

<script>

import AdminNavBar from '@/components/AdminDashboard/AdminNavBar'

export default {
  name: 'AdminDashboardView',
  components: { AdminNavBar }
}
</script>
