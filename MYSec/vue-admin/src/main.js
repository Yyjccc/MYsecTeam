import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import store from "./common/store"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import 'virtual:windi.css'
import router from './router'
import 'element-plus/theme-chalk/dark/css-vars.css'
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(store)
app.use(router)
app.use(ElementPlus)

import './router/permission.js'

app.mount('#app')
