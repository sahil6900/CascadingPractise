package com.cascading.demo.dtos;

import com.cascading.demo.enums.ProjectDomainEnum;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class EmployeeRequestDto {

    private String name;
    private String department;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
