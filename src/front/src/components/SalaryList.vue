<template>
  <div class="salaryList">
    <div class="header">
      <h3>일급 총 누적액 {{ dailyTotal }}원</h3>
    </div>
    <div class="list">
      <table>
        <thead>
        <tr>
          <th class="date">날짜</th>
          <th class="startTime">시작 시간</th>
          <th class="endTime">종료 시간</th>
          <th class="totalTime">근무 시간</th>
          <th class="hourlyWage">시급</th>
          <th class="dailyWage">일급</th>
          <th class="edit">편집</th>
        </tr>
        </thead>

        <tbody>
        <tr v-for="daily in salary" :key="daily.id">
          <td>{{ formatDate(new Date(daily.date)) }}</td>
          <td>{{ daily.startTime }}</td>
          <td>{{ daily.endTime }}</td>
          <td>{{ daily.totalTime }}시간</td>
          <td>{{ formatWage(daily.hourlyWage) }}원</td>
          <td>{{ formatWage(daily.dailyWage) }}원</td>
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
        dailyTotal: 0,
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

            this.dailyTotal = res.data
              .map(obj => obj.dailyWage)
              .reduce((d1, d2) => d1 + d2, 0)
              .toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            console.log(res.data);
          })
          .catch(e => console.log(e));
      },
      formatWage(wage) {
        let formatWage = wage.toString()
          .replace(/\B(?=(\d{3})+(?!\d))/g, ",");

        return formatWage;
      },
      formatDate(date) {
        const year = date.getFullYear();
        const month = date.getMonth() + 1;
        const day = date.getDate();
        let result = year;

        if(month < 10) {
          result += '-0' + month;
        } else {
          result += '-' + month;
        }
        if(day < 10) {
          result += '-0' + day;
        } else {
          result += '-' + day;
        }

        return result;
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
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .list {

      padding-top: 50px;

      table {
        margin: auto;
        font-size: 20px;
        width: 1200px;

        tr th {
          text-align: center;
          height: 50px;
        }

        tr td {
          text-align: center;
          height: 50px;
        }
      }
    }
  }

</style>
