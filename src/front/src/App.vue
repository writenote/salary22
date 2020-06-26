<template>
  <div id="app">
    <!-- 홈 화면 만들기 -->
<!--    <div>-->
<!--      <router-link class="btn btn-primary" to="/headerComponent">계산하러 가기</router-link>-->
<!--    </div>    -->

    <header-component/>

    <transition name="component-fade" mode="out-in">
      <router-view :key="key"/>
    </transition>
  </div>
</template>

<script>
  import HeaderComponent from "./components/HeaderComponent";
  import SalaryList from "./components/SalaryList";
  import AddSalary from "./components/AddSalary";
  import DailyPay from "./components/pay/DailyPay";
  import WeeklyPay from "./components/pay/WeeklyPay";
  import MonthlyPay from "./components/pay/MonthlyPay";
  import HolidayPay from "./components/pay/HolidayPay";
  import EventBus from "./js/EventBus";
  import { EVENT } from "./js/Constants";

  import VueRouter from 'vue-router';

  const router = new VueRouter({
    mode: 'history',
    routes: [
      {path: '/headerComponent', component: HeaderComponent},
      {path: '/list', component: SalaryList},
      {path: '/addSalary', component: AddSalary},
      {path: '/daily', component: DailyPay},
      {path: '/weekly', component: WeeklyPay},
      {path: '/monthly', component: MonthlyPay},
      {path: '/holiday', component: HolidayPay},
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
      AddSalary,
      DailyPay,
      WeeklyPay,
      MonthlyPay,
      HolidayPay
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
