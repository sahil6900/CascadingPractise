package com.cascading.demo.controller;

import com.cascading.demo.dtos.EmployeeRequestDto;
import com.cascading.demo.dtos.ProjectRequestDto;
import com.cascading.demo.enums.ProjectDomainEnum;
import com.cascading.demo.model.Employee;
import com.cascading.demo.model.Project;
import com.cascading.demo.service.EmployeeService;
import com.cascading.demo.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Employee Controller Test")
class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private ProjectService projectService;

    @Test
    @DisplayName("Should assign an existing project to an employee")
    void assignProjectToEmployeeWhenProjectExists() { // Create a mock employee and project
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setDepartment("IT");

        Project project = new Project();
        project.setId(1);
        project.setProjectName("Project A");
        project.setDepartmentCode("IT");
        project.setDomain(ProjectDomainEnum.CYBERSECURITY);

        // Mock the employee and project services
        when(employeeService.getEmployeeById(1)).thenReturn(employee);
        when(projectService.findByProjectName("Project A")).thenReturn(project);

        // Create a project request DTO
        ProjectRequestDto dto = new ProjectRequestDto();
        dto.setProjectName("Project A");
        dto.setDepartmentCode("IT");
        dto.setDomain("CYBERSECURITY");

        // Call the assignProjectToEmployee method
        ResponseEntity<?> response =
                employeeController.assignProjectToEmployee(dto, 1, "Project A");

        // Verify that the employee's project was updated
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Employee updatedEmployee = (Employee) response.getBody();
        assertEquals(project, updatedEmployee.getProject());

        // Verify that the employee and project services were called
        verify(employeeService, times(1)).getEmployeeById(1);
        verify(projectService, times(1)).findByProjectName("Project A");
        verify(employeeService, times(1)).saveEmployee(employee);
        verify(projectService, times(0)).saveProject(any(Project.class));
    }

    @Test
    @DisplayName(
            "Should create a new project and assign it to an employee when the project does not exist")
    void assignProjectToEmployeeWhenProjectDoesNotExist() { // Create a new ProjectRequestDto object
        ProjectRequestDto projectRequestDto = new ProjectRequestDto();
        projectRequestDto.setProjectName("New Project");
        projectRequestDto.setDepartmentCode("IT");
        projectRequestDto.setDomain(ProjectDomainEnum.CYBERSECURITY.toString());

        // Create a new Employee object
        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Doe");
        employee.setDepartment("IT");

        // Create a new Project object
        Project project = new Project();
        project.setId(1);
        project.setProjectName("New Project");
        project.setDepartmentCode("IT");
        project.setDomain(ProjectDomainEnum.CYBERSECURITY);

        // Mock the projectService.findByProjectName() method to return null
        when(projectService.findByProjectName(anyString())).thenReturn(null);

        // Mock the projectService.saveProject() method to return the new Project object
        when(projectService.saveProject(any(Project.class))).thenReturn(project);

        // Mock the employeeService.saveEmployee() method to return the new Employee object
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        // Call the assignProjectToEmployee() method with the new ProjectRequestDto object and the
        // employee id and project name
        ResponseEntity<?> responseEntity =
                employeeController.assignProjectToEmployee(projectRequestDto, 1, "New Project");

        // Verify that the projectService.findByProjectName() method was called once with the
        // project name
        verify(projectService, times(1)).findByProjectName("New Project");

        // Verify that the projectService.saveProject() method was called once with the new Project
        // object
        verify(projectService, times(1)).saveProject(project);

        // Verify that the employeeService.saveEmployee() method was called once with the new
        // Employee object
        verify(employeeService, times(1)).saveEmployee(employee);

        // Verify that the response status is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the response body is the new Employee object
        assertEquals(employee, responseEntity.getBody());
    }

    @Test
    @DisplayName("Should return a bad request response when the project is invalid")
    void
    saveEmployeeWhenProjectIsInvalidThenReturnBadRequest() { // Create a mock
        // EmployeeRequestDto object
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setName("John Doe");
        employeeRequestDto.setDepartment("IT");

        // Create a mock Employee object
        Employee employee = new Employee();
        employee.setName(employeeRequestDto.getName());
        employee.setDepartment(employeeRequestDto.getDepartment());

        // Create a mock Project object
        Project project = new Project();
        project.setProjectName("Project A");
        project.setDepartmentCode("IT");
        project.setDomain(ProjectDomainEnum.CYBERSECURITY);

        // Mock the findByProjectName method of ProjectService to return null
        when(projectService.findByProjectName(anyString())).thenReturn(null);

        // Call the saveEmployee method of EmployeeController
        ResponseEntity<?> responseEntity =
                employeeController.saveEmployee(
                        employeeRequestDto, project.getProjectName(), employee, project);

        // Verify that the findByProjectName method of ProjectService was called once with the
        // correct argument
        verify(projectService, times(1)).findByProjectName(project.getProjectName());

        // Verify that the saveEmployee method of EmployeeService was not called
        verify(employeeService, never()).saveEmployee(any(Employee.class));

        // Verify that the response status code is BAD_REQUEST
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Should save the employee and return the saved employee when the project is valid")
    void saveEmployeeWhenProjectIsValid() { // Create a mock EmployeeRequestDto object
        EmployeeRequestDto employeeRequestDto = new EmployeeRequestDto();
        employeeRequestDto.setName("John Doe");
        employeeRequestDto.setDepartment("IT");

        // Create a mock Employee object
        Employee employee = new Employee();
        employee.setName(employeeRequestDto.getName());
        employee.setDepartment(employeeRequestDto.getDepartment());

        // Create a mock Project object
        Project project = new Project();
        project.setProjectName("Project A");
        project.setDepartmentCode("IT");
        project.setDomain(ProjectDomainEnum.CYBERSECURITY);

        // Mock the findByProjectName method of ProjectService to return the mock Project object
        when(projectService.findByProjectName(anyString())).thenReturn(project);

        // Mock the saveEmployee method of EmployeeService to return the mock Employee object
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);

        // Call the saveEmployee method of EmployeeController with the mock EmployeeRequestDto
        // object and the project name
        ResponseEntity<?> responseEntity =
                employeeController.saveEmployee(employeeRequestDto, "Project A", employee, project);

        // Verify that the findByProjectName method of ProjectService was called once with the
        // project name
        verify(projectService, times(1)).findByProjectName("Project A");

        // Verify that the saveEmployee method of EmployeeService was called once with the mock
        // Employee object
        verify(employeeService, times(1)).saveEmployee(employee);

        // Verify that the response status code is OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verify that the response body is the mock Employee object
        assertEquals(employee, responseEntity.getBody());
    }
}