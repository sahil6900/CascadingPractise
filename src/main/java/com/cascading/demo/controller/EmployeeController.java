package com.cascading.demo.controller;

import com.cascading.demo.custom.exception.handling.BussinessExceptions;
import com.cascading.demo.custom.exception.handling.ControllerExceptions;
import com.cascading.demo.dtos.EmployeeRequestDto;
import com.cascading.demo.dtos.ProjectRequestDto;
import com.cascading.demo.enums.ProjectDomainEnum;
import com.cascading.demo.model.Employee;
import com.cascading.demo.model.Project;
import com.cascading.demo.service.EmployeeService;
import com.cascading.demo.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ProjectService projectService;

    @PostMapping("/save")
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeRequestDto dto, @RequestParam String name
            , Employee employee, Project project) {

        log.info("In EmployeeController and inside saveEmployee method");

        project = projectService.findByProjectName(name);
        try {
            if (project != null) {
                employee.setName(dto.getName());
                employee.setDepartment(dto.getDepartment());
                employee.setProject(project);
                Employee employee1 = employeeService.saveEmployee(employee);
                return ResponseEntity.status(HttpStatus.OK).body(employee1);
            } else {
                throw new ControllerExceptions("604", "Project Not found with given ProjectName" + name);
            }
        } catch (BussinessExceptions e) {
            ControllerExceptions ce = new ControllerExceptions(e.getErrorCode(), e.getErrorMessage());
            return new ResponseEntity<ControllerExceptions>(ce, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            ControllerExceptions ce = new ControllerExceptions("611","Something went wrong in controller");
            return new ResponseEntity<ControllerExceptions>(ce,HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/assign/employee/project/{eId}/{name}")
    public ResponseEntity<?> assignProjectToEmployee(@RequestBody ProjectRequestDto dto, @PathVariable Integer eId,
                                                     @PathVariable String name) {
        Employee employee = employeeService.getEmployeeById(eId);
        Project project = projectService.findByProjectName(name);
        if (project != null) {
            employee.setProject(project);
        } else {
            project = new Project();
            project.setProjectName(dto.getProjectName());
            project.setDomain(ProjectDomainEnum.valueOf(dto.getDomain()));
            project.setDepartmentCode(dto.getDepartmentCode());
            employee.setProject(projectService.saveProject(project));
        }
        employeeService.saveEmployee(employee);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }
}
