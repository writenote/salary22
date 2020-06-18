<template>
  <div class="addForm">
    <div v-if="!submitted">
      <h3>근무 시간 입력하기</h3>

      <div class="nameForm">
        <label for="name">이름</label>
        <input type="text" class="form-control" placeholder="이름 입력" id="name"
               v-model="salary.name"/>
      </div>


    </div>
  </div>
</template>

<script>
  import ApiSvc from "../js/ApiSvc";

  export default {
    name: "AddSalary",
    data() {
      return {
        salary: {
          id: 0,
          name: "",

        },
        submitted: false,
      }
    }
  }
</script>

<style lang="scss" scoped>

  .addForm {
    padding-top: 100px;
    justify-content: center;   // 화면 중앙
    text-align: center;
    max-width: 500px;
    margin: auto;

    div {

      .nameForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 50px;
          margin: auto;
        }

      }


    }
  }
</style>
