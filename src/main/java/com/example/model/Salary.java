package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Data
@NoArgsConstructor
@Entity
public class Salary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private Date date;
    private String startTime;
    private String endTime;
    private LocalDateTime startWork;
    private LocalDateTime endWork;
    private Integer day;
    private Integer night;
    private Integer totalTime;
    private Integer dailyWage;

    public Salary(String name, String startTime, String endTime) {
        this.date = new Date();
        this.name = name;
        this.startTime = formatTime(startTime);
        this.endTime = formatTime(endTime);
        this.totalTime = calTotalTime(startTime, endTime);
        this.dailyWage = calDailyWage(startTime, endTime);
    }

    public String formatTime(String time) {
        time = time.substring(0, 10) + " " + time.substring(11, 19);
        return time;
    }

    public Integer calTotalTime(String startTime, String endTime) {
        startTime = startTime.substring(0, 23);
        endTime = endTime.substring(0, 23);
        this.startWork = LocalDateTime.parse(startTime);
        this.endWork = LocalDateTime.parse(endTime);

        totalTime = Math.toIntExact(ChronoUnit.HOURS.between(this.startWork, this.endWork));
        System.out.println(totalTime);

        return totalTime;
    }

    public Integer calDailyWage(String startTime, String endTime) {
        dailyWage = 0;

        startTime = startTime.substring(11, 13);
        endTime = endTime.substring(11, 13);

        Integer startWorkTime = Integer.parseInt(startTime);
        Integer endWorkTime = Integer.parseInt(endTime);

        System.out.println("startWorkTime: " + startWorkTime);
        System.out.println("endWorkTime: " + endWorkTime);

        List<Boolean> timeList = new ArrayList<>(Arrays.asList(new Boolean[24]));  // index 0~23
        Collections.fill(timeList, Boolean.FALSE);

        if(startWorkTime < endWorkTime) {
            for(int i=startWorkTime; i<endWorkTime; i++) {
                timeList.set(i, Boolean.TRUE);
            }
        } else {
            for(int i=startWorkTime; i<=23; i++) {
                timeList.set(i, Boolean.TRUE);
            }
            for(int i=0; i<endWorkTime; i++) {
                timeList.set(i, Boolean.TRUE);
            }
        }

        Map<Integer, Integer> timeMap = new HashMap<>();
        for(int i=0; i<=23; i++) {
            if(timeList.get(i) == Boolean.TRUE) {
                if(i>=6 && i<=21) {
                    timeMap.put(i, 8950);
                } else {
                    timeMap.put(i, 12885);
                }
            }
        }

        dailyWage = timeMap.values().stream().mapToInt(i -> i).sum();   // 1번
        //dailyWage = timeMap.values().stream().reduce(0, Integer::sum);   //2번

        return dailyWage;
    }
}
