package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Date date;   // 2020-06-23T15:00:00.000+00:00
    private Integer hourlyWage;
    private String startTime;   // 2020-06-24T12:00:00.000+09:00
    private String endTime;
    private Integer totalTime;
    private Integer dailyWage;

    public Salary(Integer hourlyWage, String startTime, String endTime) {
        this.date = new Date();
        this.hourlyWage = defaultHourlyWage(hourlyWage);
        this.startTime = formatTime(startTime);
        this.endTime = formatTime(endTime);
        this.totalTime = calTotalTime(startTime, endTime);
        this.dailyWage = calDailyWage(hourlyWage, startTime, endTime);
    }

    public String formatTime(String time) {
        String formatTime = time.substring(0, 10) + " " + time.substring(11, 19);
        return formatTime;
    }

    public Integer defaultHourlyWage(Integer hourlyWage) {
        Integer wage;
        if(hourlyWage == null) {
            wage = 8590;
        } else {
            wage = hourlyWage;
        }

        return wage;
    }

    public Integer calTotalTime(String startTime, String endTime) {
        String formatStartTime = startTime.substring(0, 23);
        String formatEndTime = endTime.substring(0, 23);
        LocalDateTime workStartTime = LocalDateTime.parse(formatStartTime);  // 2020-06-24T12:00
        LocalDateTime workEndTime = LocalDateTime.parse(formatEndTime);

        totalTime = Math.toIntExact(ChronoUnit.HOURS.between(workStartTime, workEndTime));

        return totalTime;
    }

    public Integer calDailyWage(Integer hourlyWage, String startTime, String endTime) {
        dailyWage = 0;

        if(hourlyWage == null) {
            hourlyWage = 8590;
        }

        String formatStartTime = startTime.substring(11, 13);   // 09
        String formatEndTime = endTime.substring(11, 13);   // 12
        Integer workStartTime = Integer.parseInt(formatStartTime);   // 9
        Integer workEndTime = Integer.parseInt(formatEndTime);   // 12

        List<Boolean> timeList = new ArrayList<>(Arrays.asList(new Boolean[24]));  // index 0~23
        Collections.fill(timeList, Boolean.FALSE);

        if(workStartTime < workEndTime) {   // 근무 시간이 하루 안에 시작 및 종료
            for(int i=workStartTime; i<workEndTime; i++) {
                timeList.set(i, Boolean.TRUE);
            }
        } else {   // 근무 시간이 자정을 넘어감
            for(int i=workStartTime; i<=23; i++) {
                timeList.set(i, Boolean.TRUE);
            }
            for(int i=0; i<workEndTime; i++) {
                timeList.set(i, Boolean.TRUE);
            }
        }

        Map<Integer, Integer> timeMap = new HashMap<>();
        for(int i=0; i<=23; i++) {
            if(timeList.get(i) == Boolean.TRUE) {
                if(i>=6 && i<=21) {   //  && hourlyWage!=null
                    timeMap.put(i, hourlyWage);   // 주간 기본 시급
                } else {
                    timeMap.put(i, (int)(hourlyWage*1.5));   // 야간 수당
                }
            }
        }

        dailyWage = timeMap.values().stream().reduce(0, Integer::sum);   //2번

        return dailyWage;
    }
}
