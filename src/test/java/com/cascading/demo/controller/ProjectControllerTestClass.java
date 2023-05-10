package com.cascading.demo.controller;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Project Controller Test")
public class ProjectControllerTestClass {

    @InjectMocks
    private ProjectController projectController;

    @Mock
    private ProjectService projectService;

    @Mock
    private EmployeeService employeeService;

    @Test
    @DisplayName("Save the project")
    void saveProject(){
        ProjectRequestDto dto = new ProjectRequestDto();
        dto.setProjectName("My project");
        dto.setDomain(ProjectDomainEnum.BANKING.toString());
        dto.setDepartmentCode("IT");
        Project project = new Project();
        project.setId(1);
        project.setProjectName(dto.getProjectName());
        project.setDepartmentCode(dto.getDepartmentCode());
        project.setDomain(ProjectDomainEnum.valueOf(dto.getDomain()));

        when(projectService.saveProject(any(Project.class))).thenReturn(project);

        ResponseEntity<?> responseEntity =
                projectController.saveProject(dto,project);

        verify(projectService,times(1)).saveProject(project);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
    }

    @Test
    @DisplayName("Delete project from database if already exists")
    void deleteProject(){
        Project project = new Project();
        project.setId(1);
        project.setProjectName("My project");
        project.setDepartmentCode("IT");
        project.setDomain(ProjectDomainEnum.BANKING);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setName("John Wick");
        employee.setDepartment("My department");
        employee.setProject(project);

        when(employeeService.getEmployeeByProjectId(project.getId())).thenReturn(employee);
        String exceptedResponse = "Project deleted successfully";
        when(projectService.deleteProjectById(1)).thenReturn(exceptedResponse);

        ResponseEntity<?> responseEntity =
        projectController.deleteProject(1,employee);

        assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        assertEquals("Project deleted successfully with id as 1",responseEntity.getBody());
        assertNull(employee.getProject());

    }
}
