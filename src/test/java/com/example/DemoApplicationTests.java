package com.example;

import com.example.repository.SalaryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.*;
import java.util.Date;

@SpringBootTest
@Entity
class DemoApplicationTests {
    @GeneratedValue(strategy = GenerationType.AUTO)

    @Autowired
    SalaryRepository repository;

    @Test
    public int week() {
        int weekwage =0;

        Date date = new Date();

        LocalDateTime temp = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        //월(1) ~ 일(7) 요일을 숫자로 출력 (.getDayOfWeek().getValue())
        int num = temp.getDayOfWeek().getValue();

        //특정 날짜(now)에서 해당 주의 월요일 = now - (요일.getValue() - 1)
        for (num=num;num>=1;num--) {
            //weekwage += repository.findBydailyWage_endWork(temp.minusDays(num -1));
        } //주급 계산 완료

        //System.out.println(repository.findByDailyWage_EndWork(temp));
        System.out.println("현재 요일을 숫자로 표현하면 "+num);
        System.out.println("당신의 주급은 " + weekwage);

        return weekwage;


    }



}
