package com.cascading.demo.service;

import com.cascading.demo.custom.exception.handling.BussinessExceptions;
import com.cascading.demo.model.Employee;
import com.cascading.demo.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveEmployee(Employee employee) {

        if (employee.getName().isEmpty() || employee.getName().length() == 0) {
            throw new BussinessExceptions("601", "The name should not be blank");
        }
            try {
                Employee employee1 = employeeRepository.save(employee);
                return employee1;
            } catch (IllegalArgumentException exception) {
                throw new BussinessExceptions("602", "Employee cannot be blank" + exception.getMessage());
            } catch (Exception e) {
                throw new BussinessExceptions("605", "Something went wrong while saving the employee" + e.getMessage());
            }
        }
    public Employee getEmployeeByProjectId(Integer id){
        try{
            Employee employee = employeeRepository.findByProject_Id(id);

            if(employee!=null){
                return employee;
            }else {
                throw new BussinessExceptions("604","Employee not found with given projectId: "+id);
            }
        }catch (Exception e){
            throw new BussinessExceptions("607","Something wrong happen within the service layer" +e.getMessage());
        }

    }

    public Employee getEmployeeById(Integer id){
        Employee employee;
        try {
            employee = employeeRepository.findById(id).orElseThrow(() -> new BussinessExceptions("604","Employee not found with given EmployeeId: "+id));

        }catch (IllegalArgumentException e){
            throw new BussinessExceptions("602","Illegal argument passed, Have to send a Integer value" +e.getMessage());
        }
        catch (Exception e){
            throw new BussinessExceptions("607","Something went wrong in getEmployeeById method" +e.getMessage());
        }
        return employee;
    }


}
