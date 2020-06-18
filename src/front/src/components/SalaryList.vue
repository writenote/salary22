<template>
  <div class="salaryList">
    <div class="header">
      <h3>월급</h3>
      <!-- 월급 보여주기 -->
    </div>
    <div class="list">
      <!-- 일요일 이후에 주급, 일급 보여주기 -->

      <table>
        <thead>
        <tr>
          <th class="name">이름</th>
          <th class="date">날짜</th>
          <th class="startTime">시작 시간</th>
          <th class="endTime">종료 시간</th>
          <th class="totalTime">근무 시간</th>
          <th class="daily">일급</th>
          <th class="edit">편집</th>
        </tr>
        </thead>

        <tbody>
        <tr v-for="daily in salary" :key="daily.id">
          <td>{{ daily.name }}</td>
          <td>{{ daily.date }}</td>
          <td>{{ daily.startTime }}</td>
          <td>{{ daily.endTime }}</td>
          <td>{{ daily.totalTime }}</td>
          <td>{{ cal(daily.day, daily.night) }}</td>
          <td>
            <button @click="deleteRow(daily.id)" class="btn btn-danger">삭제</button>
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
  import ApiSvc from "../js/ApiSvc";

  export default {
    name: "SalaryList",
    data() {
      return {
        salary: [],
        daily: null,

      }
    },
    mounted() {
      this.getDailyWages();
    },
    methods: {
      getDailyWages() {
        ApiSvc.get("/list")
          .then(res => {
            this.salary = res.data;
            console.log(res.data);
          })
          .catch(e => console.log(e));
      },
      cal(day, night) {
        let dailyWage = 0;
        dailyWage = (day*8950) + (night*12885);

        return dailyWage;
      },
      deleteRow(id) {
        ApiSvc.delete(`/salary/${id}`)
          .then(res => {
            console.log(id);
            console.log(res.data);

            this.getDailyWages();
          })
          .catch(e => console.log(e));
      }
    },
  };
</script>

<style lang="scss" scoped>

  .salaryList {
    padding-top: 100px;

    .header {
      width: 1000px;
      margin: 0 auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .list {

      table {
        margin: auto;
        font-size: 20px;
        width: 1000px;

        tr th {
          text-align: center;
        }

        tr td {
          text-align: center;
        }
      }
    }
  }

</style>
