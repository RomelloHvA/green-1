import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import QuizComponent from '../components/quiz/QuizComponent.vue'
import QuizResultsView from '@/views/quizResultsView'
import SdgInfoPage from '@/components/LandingPage/SdgInfoPage.vue'
import QuizBuilder from '@/components/adminquiz/QuizBuilder.vue'
import QuizOverview from '@/components/adminquiz/QuizOverview.vue'
import ImageEditor from '@/components/AdminDashboard/AdminImageEdit/ImageEditor'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/about_us',
    name: 'about_us',
    redirect: '/about_us/team',
    children: [
      {
        path: 'client',
        component: () => import('../components/AboutUs/ClientComponent')
      },
      {
        path: 'team',
        component: () => import('../components/AboutUs/TeamComponent')
      },
      {
        path: 'purpose',
        component: () => import('../components/AboutUs/PurposeComponent')
      }
    ],
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path: '/:pathMatch(.*)',
    component: () => import('../components/UnknownRoute')
  },
  {
    path: '/quiz_results',
    name: 'quiz_results1',
    component: () => import('../views/quizResultsView')

  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import('../views/SignUpView')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import('../views/LogInView')
  },
  {
    path: '/profile',
    name: 'profile',
    // children: [
    //   { path: ':id', component: () => import('@/components/result/ResultPage') }
    // ],
    component: () => import('../components/profile/ProfilePage')
  },
  {
    path: '/quiz',
    name: 'Quiz',
    component: QuizComponent
  },
  {
    path: '/quiz/results',
    name: 'quiz_results',
    component: QuizResultsView
  },
  {
    path: '/sdg',
    name: 'sdgInfo',
    component: SdgInfoPage,
    children:
      [
        {
          path: ':id',
          component: SdgInfoPage,
          props: true
        }
      ]
  },
  {
    path: '/admin_dashboard',
    name: 'admin_dashboard',
    redirect: '/admin_dashboard/intro',
    children: [
      { path: 'intro', component: () => import('@/components/AdminDashboard/AdminIntroComponent') },
      {
        path: 'content',
        component: () => import('@/components/AdminDashboard/content/ContentComponent'),
        children: [
          { path: ':id', component: () => import('@/components/AdminDashboard/content/PageEditorComponent') }
        ]
      },
      {
        path: '/admin_dashboard/quiz',
        name: 'QuizOverview',
        component: QuizOverview,
        children: [
          {
            path: 'builder/:id',
            name: 'QuizBuilder',
            component: QuizBuilder,
            props: true
          }
        ]
      },
      {
        path: '/admin_dashboard/image',
        name: 'ImageOverview',
        component: ImageEditor,
        children: [
          {
            path: ':id',
            name: 'ImageChangerComponent',
            component: () => import('@/components/AdminDashboard/AdminImageEdit/ImageChangerComponent'),
            props: true
          },
          {
            path: 'preview',
            name: 'PagePreview',
            component: () => import('@/components/AdminDashboard/AdminImageEdit/PagePreview'),
            props: true,
            children: [{
              path: 'landing_page_preview',
              name: 'LandingPagePreview',
              component: () => import('@/components/AdminDashboard/AdminImageEdit/LandingPagePreview'),
              props: true
            }]
          }
        ]
      },

      { path: 'intro', component: () => import('@/components/AdminDashboard/AdminIntroComponent') },
      { path: 'users', component: () => import('@/components/AdminDashboard/AdminUserComponent') }
    ],
    component: () => import(/* webpackChunkName: "about" */ '../views/AdminDashboardView')
  },
  {
    path: '/results',
    name: 'results',
    children: [
      {
        path: ':goalId',
        component: () => import('@/components/result/ActionPlanPage')
      }
    ],
    component: () => import('@/components/result/ResultPage')
  },
  {
    path: '/admin_dashboard/isAdmin',
    name: 'adminVerify',
    component: () => import('@/components/AdminDashboard/AdminVerifyComponent')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
