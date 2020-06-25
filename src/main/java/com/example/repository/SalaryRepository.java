package com.example.repository;

import com.example.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Integer>, JpaSpecificationExecutor<Salary> {

    List<Salary> findAll();
}
