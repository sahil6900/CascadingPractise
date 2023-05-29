package com.cascading.demo.Service;

import com.cascading.demo.enums.ProjectDomainEnum;
import com.cascading.demo.model.Employee;
import com.cascading.demo.model.Project;
import com.cascading.demo.repository.EmployeeRepository;
import com.cascading.demo.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Service Test Class")
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    @DisplayName("Save employee service test")
    void saveEmployee(){
        Project project = new Project();
        project.setId(1);
        project.setProjectName("My project");
        project.setDomain(ProjectDomainEnum.BANKING);
        project.setDepartmentCode("My department");

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("Sahil Pawar");
        employee.setDepartment("IT");
        employee.setProject(project);

        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        Employee savedEmployee = employeeService.saveEmployee(employee);

        verify(employeeRepository,times(1)).save(employee);

        assertNotNull(savedEmployee);

        assertEquals(employee,savedEmployee);

    }
}
