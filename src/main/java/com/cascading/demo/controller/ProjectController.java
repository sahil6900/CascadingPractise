package com.cascading.demo.controller;

import com.cascading.demo.dtos.ProjectRequestDto;
import com.cascading.demo.enums.ProjectDomainEnum;
import com.cascading.demo.model.Employee;
import com.cascading.demo.model.Project;
import com.cascading.demo.service.EmployeeService;
import com.cascading.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/save")
    public ResponseEntity<Project> saveProject(@RequestBody ProjectRequestDto dto,Project project){
        project.setProjectName(dto.getProjectName());
        project.setDomain(ProjectDomainEnum.valueOf(dto.getDomain()));
        project.setDepartmentCode(dto.getDepartmentCode());
        Project project1 = projectService.saveProject(project);

        return ResponseEntity.status(HttpStatus.OK).body(project1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Integer id,
                                           Employee employee){

       Optional<Employee> optionalEmployee = Optional.ofNullable(employeeService.getEmployeeByProjectId(id));
       if(optionalEmployee.isPresent()){
           employee = optionalEmployee.get();
       }
        Project project = employee.getProject();
        if(project!=null){
            employee.setProject(null);
        }
        String msg = projectService.deleteProjectById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully with id as " +id);
    }

}
