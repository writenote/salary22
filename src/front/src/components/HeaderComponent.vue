<template>
  <div class="headerComponent">
    <div class="subject">
      <h1>급여 관리 시스템</h1>
    </div>
    <nav>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/list">급여 목록</router-link>
      </div>
      <div @click="refreshRouterView()">
        <router-link class="btn btn-primary" to="/addSalary">근무 시간 입력</router-link>
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

  .headerComponent {
    min-width: 1000px;
    height: 400px;
    background-color: #fff277;

    .subject {
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
    }
  }
</style>
