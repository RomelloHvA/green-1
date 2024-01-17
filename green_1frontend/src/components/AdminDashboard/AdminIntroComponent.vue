<template>
  <section class="container minvh100">
    <h5 class="card-title">Welcome to the admin dashboard</h5>
    <span class="card-text">On this page you will find all the activities an admin can do and a small explanation per activity</span>
    <div class="row m-0">
      <AdminPanelComponent title="Users" admin-description="This panel provides detailed statistics on user activities, including
    registration date, login history, and user engagement. This information helps administrators make informed decisions
    regarding user accounts. User Search and Filtering: Easily search for and filter users based on various parameters,
    such as username, email, or user role, simplifying the process of identifying and managing specific user accounts.
    User Block and Unblock. Administrators can quickly block or unblock users, allowing them to take immediate action in
    response to policy violations or other concerns. This is also where users can be made into administator if deemed fit."
                           :src="userSrc"/>

      <AdminPanelComponent title="Content"
                           admin-description="The Content Management section provides administrators
    with the necessary tools to efficiently manage and update website content. It contains search and
    filtering capabilities, quick modification features, and content publishing rights control, it enables you
    to maintain a dynamic and engaging web presence. If you are in de middle of editing a concept will be saved so you can
    continue later and finish."
                           :src="contentSrc"/>

      <AdminPanelComponent title="Quiz"
                           admin-description="The Quiz Panel provides a robust and adaptable
    solution for tailoring quizzes to your organization's needs while integrating the important concept of Sustainable
    Development Goals. Administrators can create, edit, and categorize questions, select question types, and assign
    point values, all while contributing to the promotion of SDGs and engaging users in meaningful quiz experiences."
                           :src="quizSrc"/>
    </div>
  </section>
</template>
<script>
import AdminPanelComponent from '@/components/AdminDashboard/PanelComponent/AdminPanelComponent'

export default {
  name: 'IntroComponent',
  components: { AdminPanelComponent },
  data () {
    return {
      userSrc: require('@/assets/img/admin-dashboard/User-statistics.png'),
      contentSrc: require('@/assets/img/admin-dashboard/content-management-icon.png'),
      quizSrc: require('@/assets/img/admin-dashboard/quiz-edit-icon.png')
    }
  }
}
</script>
