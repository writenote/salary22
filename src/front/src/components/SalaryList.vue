<template>
  <div class="salaryList">
    <div class="header">
      <div style="border: 5px outset #cbf0ff; height: auto; padding: 25px;">
        <h3>{{ selectedDate.year }}년 {{ selectedDate.month }}월 일급 총 누적액 {{ dailyTotal }}원</h3>
      </div>
      <div>
        <div @click="refreshRouterView()">
          <router-link class="btn btn-primary" to="/addSalary">근무 시간 입력</router-link>
        </div>
        <div class="search">
          <b-dropdown
            id="dropdown-year" class="m-2" required
            :text="selectedYear"
          >
            <b-dropdown-item
              v-for="year in YearList" :key="year.id"
              @click="selectYear(year)"
            >
              {{ year }}
            </b-dropdown-item>
          </b-dropdown>

          <b-dropdown
            id="dropdown-month" class="m-2" required
            :text="selectedMonth"
          >
            <b-dropdown-item
              v-for="month in MonthList" :key="month.id"
              @click="selectMonth(month)"
            >
              {{ month }}
            </b-dropdown-item>
          </b-dropdown>

          <button @click="getDailyWages()" class="btn btn-success">검색</button>
        </div>
      </div>

    </div>
    <div class="list">
      <h6>※ 22:00 ~ 06:00 사이 근무 시, 야간수당(시급의 1.5배)이 적용됩니다.</h6>
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
  import EventBus from "../js/EventBus";
  import {EVENT} from "../js/Constants";

  export default {
    name: "SalaryList",
    data() {
      return {
        salary: [],
        daily: null,
        dailyTotal: 0,
        selectedDate: {
          year: "",
          month: "",
        },
        selectedYear: '년도 선택',
        YearList: ['2020', '2021', '2022', '2023', '2024', '2025'],

        selectedMonth: '월 선택',
        MonthList: ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'],
      }
    },
    mounted() {
      const _date = new Date();
      const _curYear = _date.getFullYear();
      const _curMonth = _date.getMonth() + 1;

      // 처음에 현재 년/월에 맞는 데이터가 들어오도록
      this.selectedDate = {
        year: `${_curYear}`,
        month: `${_curMonth < 10 ? '0':''}${_curMonth}`,
      };
      this.getDailyWages();
    },
    methods: {
      refreshRouterView() {
        EventBus.$emit(EVENT.REFRESH_ROUTER_VIEW);
      },
      selectYear(selectedItem) {
        this.selectedYear = selectedItem;
        this.selectedDate.year = this.selectedYear;
      },
      selectMonth(selectedItem) {
        this.dailyTotal = 0;   // 검색 전 월까지 선택했을 때 누적 지출액 0으로
        this.selectedMonth = selectedItem;
        this.selectedDate.month = this.selectedMonth;
      },
      getDailyWages() {
        const requestData = {
          year: this.selectedDate.year,
          month: this.selectedDate.month
        };

        ApiSvc.post("/list", requestData)   // PostMapping 수정
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
    margin: 100px;

    .header {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .list {
      padding-top: 50px;
      padding-bottom: 100px;

      h6 {
        text-align: right;
        color: red;
        margin: auto;
        width: 1200px;
      }

      table {
        border-collapse: collapse;
        border-top: 3px solid #168;

        margin: auto;
        font-size: 20px;
        width: 1200px;

        tr th {
          color: #168;
          background: #f0f6f9;

          text-align: center;
          height: 50px;
        }

        tr:nth-child(even) {
          background: #f1efef;
        }

        tr td {
          text-align: center;
          height: 50px;
        }
      }
    }
  }
</style>
