package com.cascading.demo.repository;

import com.cascading.demo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project,Integer> {

    @Query("select p from Project p where p.projectName=?1")
    Project findByProjectName(String name);


}
