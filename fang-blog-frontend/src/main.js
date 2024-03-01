// basic vue component
import { createApp } from 'vue'
import App from './App.vue'

// element-plus
import ElementPlus from "element-plus"
import "element-plus/dist/index.css"
import locale from "element-plus/dist/locale/en.js";    // system language of element-plus

// vue router
import router from "@/router";

// pinia
import { createPinia } from "pinia";
import { createPersistedState } from 'pinia-persistedstate-plugin';


const app = createApp(App);
app.use(ElementPlus, { locale });

app.use(router);

const pinia = createPinia();
const persistedstate = createPersistedState();
pinia.use(persistedstate);
app.use(pinia);

app.mount('#app');