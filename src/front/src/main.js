import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';
import BootstrapVue from 'bootstrap-vue';
import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-vue/dist/bootstrap-vue.css';
//import {FadeImages} from 'vue-fade-images';

Vue.config.devtools = true;
Vue.config.productionTip = false;
Vue.use(VueRouter);
Vue.use(BootstrapVue);
//Vue.use(FadeImages);

new Vue({
  VueRouter,
  render: h => h(App)
}).$mount("#app");
