<template>
  <div id="app">
    <!-- 홈 화면 만들기 -->
    <div class="home" v-if="home">
      <div class="imageArea" onload="showImage()">
        <img id="introImg" border="0">
        <div class="empty"></div>
        <button @click="goMenu()" class="btn btn-primary">계산하러 가기</button>
      </div>

      <div class="intro">

      </div>
    </div>

    <div v-else>
      <header-component/>

      <transition name="component-fade" mode="out-in">
        <router-view :key="key"/>
      </transition>
    </div>
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
  import Vue from "vue";
  import Datetime from 'vue-datetime';
  import 'vue-datetime/dist/vue-datetime.css';
  import {Settings} from 'luxon';
  import VueRouter from 'vue-router';

  Vue.use(Datetime);
  Settings.defaultLocale = 'ko';

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
        home: true,
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
    methods: {
      goMenu() {
        this.home = false;
      },
      showImage() {
        var imgArray = new Array();
        imgArray[0] = "../../front/src/assets/file.jpg";
        imgArray[1] = "../../front/src/assets/document.jpg";
        imgArray[2] = "../../front/src/assets/money.jpg";
        imgArray[3] = "../../front/src/assets/number.jpg";


        var imgNum = Math.round(Math.random() * 3);
        var objImg = document.getElementById("introImg");
        objImg.src = imgArray[imgNum];

        setTimeout("showImage()", 200);
      }
    }
  }
</script>

<style lang="scss" scoped>

  #app {
    height: 900px;

    .home {

      .imageArea {
        min-width: 1000px;
        height: 600px;
        //background-image: url("../../front/src/assets/file.jpg");
        background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;

        .empty {
          height: 420px;
        }

        .btn {
          display: flex;
          width: 185px;
          text-align: center;
          font-size: 25px;
          margin: auto;
          background-color: transparent;
          border:2px white solid;
        }
      }
    }
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
