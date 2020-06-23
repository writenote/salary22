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
        this.dailyWage = calWage(startTime, endTime);
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

    public Integer calWage(String startTime, String endTime) {
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

    public Integer calDailyWage(String startTime, String endTime) {
        startTime = startTime.substring(11, 23);
        endTime = endTime.substring(11, 23);
        LocalTime startWorkTime = LocalTime.parse(startTime);
        LocalTime endWorkTime = LocalTime.parse(endTime);

        System.out.println("startWorkTime: " + startWorkTime);
        System.out.println("endWorkTime: " + endWorkTime);

        LocalTime dayTime = LocalTime.of(06, 0,0,0);
        LocalTime nightTime = LocalTime.of(22, 0,0,0);
        LocalTime midnight = LocalTime.of(00, 0,0,0);

        day = 0;
        night = 0;


        if(startWorkTime.isAfter(dayTime) && endWorkTime.isBefore(nightTime) && startWorkTime.isBefore(endWorkTime)) {
            System.out.println("1번상황");
            day = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, endWorkTime));
        } else if(endWorkTime.isBefore(nightTime)) {
            System.out.println("2번상황");
            day = Math.toIntExact(ChronoUnit.HOURS.between(dayTime, endWorkTime));
            night = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, dayTime));
        } else if(startWorkTime.isAfter(dayTime)) {
            System.out.println("3번상황");
            day = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, nightTime));
            night = Math.toIntExact(ChronoUnit.HOURS.between(nightTime, endWorkTime));
        } else if(startWorkTime.isBefore(dayTime) && endWorkTime.isAfter(nightTime)) {
            System.out.println("4번상황");
            day = 16;
            night = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, dayTime)) +
                    Math.toIntExact(ChronoUnit.HOURS.between(nightTime, endWorkTime));
        } else if(startWorkTime.isBefore(nightTime) && endWorkTime.isAfter(dayTime) && startWorkTime.isAfter(endWorkTime)) {   // 문제
            System.out.println("5번상황");
            day = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, nightTime)) +
                    Math.toIntExact(ChronoUnit.HOURS.between(dayTime, endWorkTime));
            night = 8;
        } else if(startWorkTime.isAfter(nightTime) && endWorkTime.isBefore(dayTime)) {   // 문제
            System.out.println("6번상황");
            night = Math.toIntExact(ChronoUnit.HOURS.between(startWorkTime, midnight)) +
                    Math.toIntExact(ChronoUnit.HOURS.between(midnight, endWorkTime));
        }

        System.out.println("day: " + day);
        System.out.println("night: " + night);

        dailyWage = (day * 8950) + (night * 12885);

        return dailyWage;
    }

    public Integer CalculateWage(String startTime, String endTime) {
        startTime = startTime.substring(11, 13);
        endTime = endTime.substring(11, 13);

        Integer startWorkTime = Integer.parseInt(startTime);
        Integer endWorkTime = Integer.parseInt(endTime);

        System.out.println("startWorkTime: " + startWorkTime);
        System.out.println("endWorkTime: " + endWorkTime);

        day = 0;
        night = 0;

        if(startWorkTime >= 6 && endWorkTime <= 22 && startWorkTime < endWorkTime) {
            day = endWorkTime - startWorkTime;
        } else if(endWorkTime <= 22) {
            day = endWorkTime - 6;
            night = 6 - startWorkTime;
        } else if(startWorkTime >= 6) {
            day = 22 - startWorkTime;
            night = endWorkTime - 22;
        } else if(startWorkTime < 6 && endWorkTime > 22) {
            day = 16;
            night = (6 - startWorkTime) + (endWorkTime - 22);
        } else if(startWorkTime < 22 && endWorkTime > 6 && startWorkTime > endWorkTime) {
            day = (22 - startWorkTime) + (endWorkTime - 6);
            night = 8;
        } else if(startWorkTime >= 22 && endWorkTime <= 6) {
            night = (24 - startWorkTime) + endWorkTime;
        }

        System.out.println("day: " + day);
        System.out.println("night: " + night);

        dailyWage = (day * 8950) + (night * 12885);

        return dailyWage;
    }
}
