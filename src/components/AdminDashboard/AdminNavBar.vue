<template>
  <ul class="nav nav-tabs card-header-tabs">
    <AdminNavBarItem text="Intro" route="/admin_dashboard/intro"/>
    <AdminNavBarItem text="Users" route="/admin_dashboard/users"/>
    <AdminNavBarItem text="Content" route="/admin_dashboard/content"/>
    <AdminNavBarItem text="Quiz" route="/admin_dashboard/quiz"/>
  </ul>
</template>
<script>
import AdminNavBarItem from '@/components/AdminDashboard/AdminNavBarItem'

export default {
  name: 'AdminNavBar',
  components: { AdminNavBarItem }
}
</script>
