<template>
  <div id="app">
    <div class="home" v-if="home">
      <div class="imageArea">
        <fade-images :delay="5000" :height="'100%'" :width="'100%'"
                     :images="images"/>
        <div class="image-cover"></div>
        <div class="subject">
          <h1>급여 계산기</h1>
          <button @click="goMenu()" class="btn btn-primary">계산하러 가기</button>
        </div>
      </div>

      <div class="intro">
        <h1>급여 계산기는 5가지 기능을 제공합니다.</h1>
        <div class="boxes">
          <div class="box">
            <h2>일급 목록</h2>
            <h4>매일 근무 시간을 입력하여 일급을 확인하고 월별로 그 누적액을 확인할 수 있습니다.</h4>
          </div>
          <div class="box">
            <h2>일급 계산하기</h2>
            <h4>하루 근무 시간과 시급을 입력하여 일급을 확인할 수 있습니다. (야간수당 자동 계산)</h4>
          </div>
          <div class="box">
            <h2>주급 계산하기</h2>
            <h4>하루 근무 시간과 시급, 주 근무 일수를 입력하여 주휴수당이 포함된 주급을 확인할 수 있습니다.</h4>
          </div>
          <div class="box">
            <h2>월급 계산하기</h2>
            <h4>하루 근무 시간과 시급, 월 근무 일수를 입력하여 주휴수당이 포함된 월급을 확인할 수 있습니다.</h4>
          </div>
          <div class="box">
            <h2>주휴수당 계산하기</h2>
            <h4>주 근무 시간과 시급을 입력하여 주휴수당을 확인할 수 있습니다.</h4>
          </div>
        </div>
      </div>

      <div class="footer">
        <h3>Footer 내용 입력</h3>
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
          {src: require("../../front/src/assets/businessmen-standing-coins2.jpg")},
          {src: require("../../front/src/assets/coin-tower.jpg")},
          {src: require("../../front/src/assets/red-pencil.jpg")},
          {src: require("../../front/src/assets/working-people.jpg")},
          {src: require("../../front/src/assets/calculator.jpg")},
          {src: require("../../front/src/assets/checkcard.jpg")},
          {src: require("../../front/src/assets/bank-book.jpg")},
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
  @import url(//fonts.googleapis.com/earlyaccess/jejugothic.css);
  @import url(//fonts.googleapis.com/earlyaccess/kopubbatang.css);
  @import url(//fonts.googleapis.com/earlyaccess/notosansgothic.css);
  @font-face { font-family: 'InfinitySans-RegularA1'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/InfinitySans-RegularA1.woff') format('woff'); font-weight: normal; font-style: normal; }
  @font-face { font-family: 'BBTreeGR'; src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_nine_@1.1/BBTreeGR.woff') format('woff'); font-weight: normal; font-style: normal; }

  #app {
    //font-family: 'Nanum Gothic Coding', monospace;
    //font-family: 'Jeju Gothic', sans-serif;
    //font-family: 'KoPub Batang', serif;
    //font-family: 'Noto Sans Gothic', sans-serif;
    //font-family: 'InfinitySans-RegularA1';
    //font-family: 'BBTreeGR';
    font-family: 'NanumSquare', sans-serif !important;

    .home {
      overflow-x: hidden;

      .imageArea {
        width: 100vw;
        height: 100vh;
        background-repeat: no-repeat;
        background-position: center center;
        position: relative;

        .image-cover {
          position: absolute;
          width: 100%;
          height: 100%;
          background-color: rgba(0, 0, 0, 0.2);
          z-index: 1;
        }

        .subject {
          position: absolute;
          text-align: center;
          width: 100%;
          height: 100%;
          z-index: 2;

          h1 {
            width: 100vw;
            margin: 15% auto;
            color: white;
            font-size: 80px;
          }

          .btn {
            width: 300px;
            font-size: 40px;
            text-align: center;
            background-color: transparent;
            border: 3px white solid;
          }
        }
      }

      .intro {
        background-color: #d3d3d3;
        padding: 80px;
        text-align: center;
        //display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙
        //justify-content: center;   // 화면 중앙

        .boxes {
          display: flex;
          justify-content: center;
          text-align: center;

          .box {
            margin: 30px;
            padding: 25px;
            width: 430px;
            height: 450px;
            border-radius: 20px;
            background-color: white;

            h2 {
              font-weight: bold;
            }

            h4 {
              margin-top: 20px;
            }
          }
        }
      }

      .footer {
        background-color: #969696;

        h3 {
          padding: 30px;
          color: white;
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
