package com.example.Service;

import com.example.model.Salary;
import com.example.repository.SalaryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@org.springframework.stereotype.Service
@Transactional
public class Service {

    @Autowired
    SalaryRepository repository;

}
