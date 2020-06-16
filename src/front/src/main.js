import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router';

Vue.config.devtools = true;
Vue.config.productionTip = false;
Vue.use(VueRouter);

new Vue({
  VueRouter,
  render: h => h(App)
}).$mount("#app");
