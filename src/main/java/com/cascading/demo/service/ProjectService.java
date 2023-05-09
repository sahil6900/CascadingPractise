package com.cascading.demo.service;

import com.cascading.demo.model.Project;
import com.cascading.demo.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    public Project saveProject(Project project) {
        Project saveProject = projectRepository.save(project);

        return saveProject;
    }

    public Project findByProjectName(String name){
        Project ProjectByName = projectRepository.findByProjectName(name);

        return ProjectByName;
    }

    public String deleteProjectById(Integer id) {
        projectRepository.deleteById(id);

        return "project deleted successfully";
    }
}
