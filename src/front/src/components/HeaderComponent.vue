<template>
  <div class="headerComponent">
    <div class="subject">
      <h1>급여 관리 시스템</h1>
    </div>
    <nav>
      <div @click="refreshRouterView()">
        <router-link class="btn bestcss" to="/list">일급 목록</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn bestcss" to="/daily">일급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn bestcss" to="/weekly">주급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn bestcss" to="/monthly">월급 계산하기</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn bestcss" to="/holiday">주휴수당 계산하기</router-link>
      </div>
    </nav>
  </div>
</template>

<script>
  import EventBus from "../js/EventBus";
  import { EVENT } from "../js/Constants";

  export default {
    name: "HeaderComponent",
    methods: {
        refreshRouterView() {
          EventBus.$emit(EVENT.REFRESH_ROUTER_VIEW);
        }
    }
  }
</script>

<style lang="scss" scoped>
  @import url('https://fonts.googleapis.com/css2?family=Noto+Serif+KR:wght@500&display=swap');

  .headerComponent {
    min-width: 1000px;
    height: 400px;
    background-color: #fff277;

    .subject {
      font-family: 'Noto Serif KR', serif;
      display: flex;
      justify-content: center;
      align-items: center;
      height: 300px;
    }

    nav {
      display: flex;
      justify-content: center;
      align-items: center;

      .btn {
        margin: 5px;
      }


      .bestcss {
        box-shadow: 0px 0px 0px 0px #f0f7fa;
        background:linear-gradient(to bottom, #33bdef 5%, #019ad2 100%);
        background-color:#33bdef;
        border-radius:11px;
        display:inline-block;
        cursor:pointer;
        color:#ffffff;
        font-family:Arial;
        font-size:15px;
        padding:11px 23px;
        text-decoration:none;
      }
      .bestcss:hover {
        background:linear-gradient(to bottom, #019ad2 5%, #33bdef 100%);
        background-color:#019ad2;
      }
      .bestcss:active {
        position:relative;
        top:1px;
      }







    }


  }
</style>
