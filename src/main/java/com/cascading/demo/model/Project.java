package com.cascading.demo.model;

import com.cascading.demo.enums.ProjectDomainEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String projectName;
    private String departmentCode;

    @Enumerated(EnumType.STRING)
    private ProjectDomainEnum domain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }



    public ProjectDomainEnum getDomain() {
        return domain;
    }

    public void setDomain(ProjectDomainEnum domain) {
        this.domain = domain;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", departmentName='" + projectName + '\'' +
                ", departmentCode='" + departmentCode + '\'' +
                ", domain=" + domain +'}';
    }
}
