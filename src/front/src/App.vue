<template>
  <div id="app">
    <div class="home" v-if="home">
      <div class="imageArea">
        <div class="images">
          <fade-images :delay="5000" :width="2000" :height="700"
                       :images="images"/>
          <div class="image-cover"></div>
        </div>
        <div class="subject">
          <h1>급여 계산기</h1>
          <button @click="goMenu()" class="btn btn-primary">계산하러 가기</button>
        </div>
      </div>

      <div class="intro">
        <h1>기능 소개</h1>
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
  import FadeImages from 'vue-fade-images'

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
        images: [
          {src: require("../../front/src/assets/file.jpg")},
          {src: require("../../front/src/assets/document.jpg")},
          {src: require("../../front/src/assets/money.jpg")},
          {src: require("../../front/src/assets/number.jpg")}
        ]
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
      HolidayPay,
      FadeImages
    },
    methods: {
      goMenu() {
        this.home = false;
      },
    }
  }
</script>

<style lang="scss" scoped>

  #app {
    height: 1000px;

    .home {

      .imageArea {
        min-width: 1000px;
        height: 700px;
        //background-size: cover;
        background-repeat: no-repeat;
        background-position: center center;

        .images {
          //background-size: cover;
          position: relative;

          .image-cover {
            position: absolute;
            height: 700px;
            width: 2000px;
            background-color: rgba(0, 0, 0, 0.3);
            z-index: 1;
          }
        }

        .subject {
          position: absolute;
          text-align: center;
          z-index: 2;

          h1 {
            min-width: 2000px;
            color: white;
            margin: 200px auto;
          }

          .btn {
            width: 185px;
            text-align: center;
            font-size: 25px;
            background-color: transparent;
            border: 2px white solid;
          }
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
