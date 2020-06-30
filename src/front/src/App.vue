<template>
  <div id="app">
    <div class="home" v-if="home">
      <div class="imageArea">
        <div class="images">
          <fade-images :delay="5000" :height="700" :width="'100%'"
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
        <h2>문장을 더 추가하면</h2>
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
          {src: require("../../front/src/assets/businessmen-standing-coins.jpg")},
          {src: require("../../front/src/assets/coin-tower.jpg")},
          {src: require("../../front/src/assets/red-pencil.jpg")},
          {src: require("../../front/src/assets/working-people.jpg")},
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
    height: 100%;

    .home {
      height: 100%;

      .imageArea {
        min-width: 1000px;
        height: 700px;
        background-repeat: no-repeat;
        background-position: center center;

        .images {
          position: relative;
          width: 100vw;

          .image-cover {
            position: absolute;
            height: 700px;
            width: 100vw;
            background-color: rgba(0, 0, 0, 0.5);
            z-index: 1;
          }
        }

        .subject {
          position: absolute;
          text-align: center;
          z-index: 2;

          h1 {
            //font-family: "나눔스퀘어라운드 Bold";
            //font-family: 'NanumBarunGothic', sans-serif;
            font-family: 'Nanum Gothic Coding', monospace;
            width: 100vw;
            color: white;
            margin: 200px auto;
            font-size: 70px;
          }

          .btn {
            width: 250px;
            text-align: center;
            font-size: 30px;
            font-family: 'Nanum Gothic Coding', monospace;
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
