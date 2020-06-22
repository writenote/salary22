package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalTime = calTotalTime(startTime, endTime);
        this.dailyWage = calDailyWage(startTime, endTime);
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

//    public Integer CalculateDay() {
//        day = 0;
//
//        if(startTime >= 6 && endTime <= 22) {
//            day = endTime - startTime;
//        }
//        if(endTime <= 22) {
//            day = endTime - 6;
//        }
//        if(startTime >= 6) {
//            day = 22 - startTime;
//        }
//        if(startTime < 6 && endTime > 22) {
//            day = 16;
//        }
//        if(startTime < 22 && endTime > 6) {
//            day = (22 - startTime) + (endTime - 6);
//        }
//        return day;
//    }
//
//    public Integer CalculateNight() {
//        night = 0;
//
//        if(endTime <= 22) {
//            night = 6 - startTime;
//        }
//        if(startTime >= 6) {
//            night = endTime - 22;
//        }
//        if(startTime >= 22 && endTime <= 6) {
//            night = (24 - startTime) + endTime;
//        }
//        if(startTime < 6 && endTime > 22) {
//            night = (6 - startTime) + (endTime - 22);
//        }
//        if(startTime < 22 && endTime > 6) {
//            night = 8;
//        }
//        return night;
//    }
}
