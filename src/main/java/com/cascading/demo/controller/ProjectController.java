package com.cascading.demo.controller;

import com.cascading.demo.model.Employee;
import com.cascading.demo.model.Project;
import com.cascading.demo.service.EmployeeService;
import com.cascading.demo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private EmployeeService employeeService;



    @PostMapping("/save")
    public ResponseEntity<Project> saveProject(@RequestBody Project project){
        Project project1 = projectService.saveProject(project);

        return ResponseEntity.status(HttpStatus.OK).body(project1);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable Integer id,
                                           Employee employee){

        employee = employeeService.getEmployeeByProjectId(id);
        Project project = employee.getProject();
        if(project!=null){
            employee.setProject(null);
        }
        String msg = projectService.deleteProjectById(id);

        return ResponseEntity.status(HttpStatus.OK).body("Project deleted successfully with id as" +id);
    }

}
