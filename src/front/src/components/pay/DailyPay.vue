<template>
  <div class="addForm">
    <div v-if="!submitted">
      <h3>일급 계산하기</h3>

      <div class="dateTimePicker">
        <div class="startTime">
          <label for="startTime">시작 시간</label>
          <datetime type="datetime" class="form-control" id="startTime"
                    v-model="once.startTime" placeholder="날짜 선택"
                    value-zone="Asia/Seoul"
                    :minute-step="60"
          ></datetime>
        </div>

        <div class="endTime">
          <label for="endTime">종료 시간</label>
          <datetime type="datetime" class="form-control" id="endTime"
                    v-model="once.endTime" placeholder="날짜 선택"
                    value-zone="Asia/Seoul"
                    :minute-step="60"
          ></datetime>
        </div>
      </div>

      <div class="hourlyWageForm">
        <label for="hourlyWage">시급</label>
        <input type="text" class="form-control" placeholder="최저시급 8,590원" id="hourlyWage"
               v-model="once.hourlyWage"/>
      </div>

      <div class="resultForm">
        <label for="result">임시 결과</label>
        <p class="form-control" id="result">{{ once.result }}</p>
      </div>

      <button v-on:click="onceadd" class="btn btn-success">계산하기</button>
    </div>


  </div>

</template>



<script>
  import Vue from 'vue';
  import Datetime from 'vue-datetime';
  import 'vue-datetime/dist/vue-datetime.css';
  import {Settings} from 'luxon';

  Vue.use(Datetime);
  Settings.defaultLocale = 'ko';

  export default {
    name: "DailyPay",
    data() {
      return {
        once: {
          id: 0,
          startTime: "",
          endTime: "",
          hourlyWage: "",
          result: "",
        },
        submitted: false,
      }
    },
    methods: {
      onceadd() {
        this.result = formatTime(this.startTime);
      },
    }
  };


  function formatTime(String) {
    var f  = String.substring(0, 10) + " " + String.substring(11, 19);
    // 2020-06-25 09:00:00
    return f;
  }

 /* function calTotalTime(f1, f2) {
    formatTime1 = f1.substring(0, 23);
    formatTime2 = f2.substring(0, 23);
    workStartTime = LocalDateTime.parse(formatTime1);  // 2020-06-24T12:00
    workEndTime = LocalDateTime.parse(formatTime2);

    totalTime = Math.toIntExact(ChronoUnit.HOURS.between(workStartTime, workEndTime));

    return totalTime;
  }

  function Hashtest(){//test
    try{
      var map = new HashMap();
      map.put("1", "2");
      //map.put("3", "4");
      //map.remove("1");
      var allTemp = map.getAll();
      for(i in allTemp){  alert(i);}
      alert(map.toString());
    }catch(e){alert(e);}
  }

  var timeMap = new HashMap();
  timeMap
  for(var i=0; i<=23; i++) {
    if(timeList.get(i) == Boolean.TRUE) {
      if(i>=6 && i<=21) {   //  && hourlyWage!=null
        timeMap.put(i, hourlyWage);   // 주간 기본 시급
      } else {
        timeMap.put(i, (int)(hourlyWage*1.5));   // 야간 수당
      }
    }
  }
*/
</script>

<style lang="scss" scoped>

  .addForm {
    padding-top: 100px;
    justify-content: center;   // 화면 중앙
    text-align: center;
    max-width: 500px;
    margin: auto;

    div {

      .dateTimePicker {

        .startTime {
          padding-top: 50px;
          display: flex;
          align-items: center;

          label {
            min-width: 100px;
            margin: auto;
            text-align: left;
          }
        }

        .endTime {
          padding-top: 50px;
          display: flex;
          align-items: center;

          label {
            min-width: 100px;
            margin: auto;
            text-align: left;
          }
        }
      }

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

      .resultForm {

        padding-top: 50px;
        display: flex;   // 옆으로 나란히
        align-items: center;   // 위아래 중앙

        label {
          min-width: 100px;
          margin: auto;
          text-align: left;
        }

      }

      .btn {
        margin: 50px;
      }
    }

    div {

      .btn {
        margin: 100px;
      }
    }
  }
</style>
