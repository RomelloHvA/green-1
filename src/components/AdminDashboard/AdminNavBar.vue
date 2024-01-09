<template>
  <ul class="nav nav-tabs card-header-tabs">
    <AdminNavBarItem text="Intro" route="/admin_dashboard/intro"/>
    <AdminNavBarItem text="Users" route="/admin_dashboard/users"/>
    <AdminNavBarItem text="Content" route="/admin_dashboard/content"/>
    <AdminNavBarItem text="Quiz" route="/admin_dashboard/quiz"/>
    <AdminNavBarItem text="Action Plans" route="/admin_dashboard/action_plans"/>
    <AdminNavBarItem text="Images" route="/admin_dashboard/image"/>
    <AdminNavBarItem text="Sectors" route="/admin_dashboard/sectors"/>
  </ul>
</template>
<script>
import AdminNavBarItem from '@/components/AdminDashboard/AdminNavBarItem'

export default {
  name: 'AdminNavBar',
  components: { AdminNavBarItem }
}
</script>
