import 'bootstrap/dist/css/bootstrap.css'
import { createApp } from 'vue'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'
import App from './App.vue'
import router from './router'
import store from './store'

createApp(App).use(store).use(router).mount('#app')
