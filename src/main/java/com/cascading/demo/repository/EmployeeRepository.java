package com.cascading.demo.repository;

import com.cascading.demo.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> {

    Employee findByProject_Id(Integer id);
}
