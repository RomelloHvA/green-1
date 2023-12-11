import { createApp } from 'vue'
import 'bootstrap/dist/css/bootstrap.css'
import ToastPlugin from 'vue-toast-notification'
import 'vue-toast-notification/dist/theme-bootstrap.css'
import App from './App.vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import router from './router'
import store from './store'
import '@/assets/main.css'

createApp(App).use(store).use(router).use(ToastPlugin).mount('#app')
