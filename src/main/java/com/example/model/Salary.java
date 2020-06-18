package com.example.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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
    private Integer day;
    private Integer night;
    private Integer totalTime;

    public Salary(String name, String startTime, String endTime) {
        this.date = new Date();
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.day = 0;
        this.night = 0;
        this.totalTime = 0;
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
//
//    public Integer total() {
//        totalTime = 0;
//
//        totalTime = this.day + this.night;
//        return totalTime;
//    }
}
