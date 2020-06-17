<template>
  <div id="app">
    <header-component/>

    <transition name="component-fade" mode="out-in">
      <router-view :key="key"/>
    </transition>
  </div>
</template>

<script>
  import HeaderComponent from "./components/HeaderComponent";
  import SalaryList from "./components/SalaryList";
  import EventBus from "./js/EventBus";
  import { EVENT } from "./js/Constants";

  import VueRouter from 'vue-router';

  const router = new VueRouter({
    mode: 'history',
    routes: [
      { path: '/list', component: SalaryList },

    ]
  });

  export default {
    name: 'app',
    data () {
      return {
        key: 0,
      }
    },
    created() {
      EventBus.$on(EVENT.REFRESH_ROUTER_VIEW, () => {
        this.key++;
      });
    },
    router,
    components: {
      HeaderComponent,
      SalaryList,

    },
  }
</script>

<style lang="scss" scoped>

  #app {
    height: 900px;
  }

  .component-fade-leave-active {
    opacity: 0;
  }

  .component-fade-enter, .component-fade-leave-to {
    opacity: 0;
  }

  .component-fade-enter-active {
    transition: opacity .5s ease;
  }
</style>
