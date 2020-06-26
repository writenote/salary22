<template>
  <div class="weekly">
    <div class="subject">
      <h3>주급 계산하기</h3>
    </div>
    <div class="inputForm">
      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"/> <!--v-model="salary.hourlyWage"-->
      </div>

      <div class="startTime">
        <label for="startTime">시작 시간</label>
        <datetime type="time" id="startTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"> <!--v-model="time"-->
        </datetime>
      </div>

      <div class="endTime">
        <label for="endTime">종료 시간</label>
        <datetime type="time" id="endTime" placeholder="시간 선택"
                  value-zone="Asia/Seoul"
                  :minute-step="60"> <!--v-model="time"-->
        </datetime>
      </div>

      <div class="workingDays">
        <label for="endTime">한 주 근무 일수</label>
        <b-dropdown
          id="workingDays" class="m-2" required
          :text="selectedDays"
        >
          <b-dropdown-item
            v-for="days in daysList" :key="days.id"
            @click="selectDays(days)"
          >
            {{ days }}
          </b-dropdown-item>
        </b-dropdown>
      </div>
    </div>

    <div class="btnArea">
      <button @click="calWeeklyPay()" class="btn btn-success">계산하기</button>
    </div>
  </div>
</template>

<script>
  export default {
    name: "WeeklyPay",
    data() {
      return {
        weeklyPay: {
          days: "",
        },
        selectedDays: '근무 일수 선택',
        daysList: ['1', '2', '3', '4', '5', '6', '7']
      }
    },
    methods: {
      selectDays(selectedItem) {
        this.selectedDays = selectedItem;
        this.weeklyPay.days = this.selectedDays;
      },
      calWeeklyPay() {

      },
    }

  }
</script>

<style lang="scss" scoped>

  .weekly {
    padding-top: 100px;

    .subject {
      width: 1000px;
      margin: auto;
      display: flex;
      align-items: center;
      justify-content: space-between;
    }

    .inputForm {

      justify-content: center;   // 화면 중앙
      text-align: center;
      max-width: 500px;
      margin: auto;

      .hourlyWageForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

      }

      .startTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }

        dateTime {
          //width: 500px;
        }
      }

      .endTime {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }

      .workingDays {
        padding-top: 50px;
        display: flex;
        align-items: center;

        label {
          min-width: 100px;
          margin-left: 0px;
          text-align: left;
        }
      }
    }

    .btnArea {
      padding-top: 50px;
      display: flex;

      width: 100px;
      justify-content: center;
      font-size: 18px;
      margin: 0 auto;
    }
  }
</style>
