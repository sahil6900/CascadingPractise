package com.cascading.demo.service;

import com.cascading.demo.model.Employee;
import com.cascading.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {
        Employee employee1 = employeeRepository.save(employee);

        return employee1;
    }

    public Employee getEmployeeByProjectId(Integer id){
        Employee employee = employeeRepository.findByProject_Id(id);
        return employee;
    }

    public Employee getEmployeeById(Integer id){
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("No id found with " + id));

        return employee;
    }


}
