package com.cascading.demo.Service;

import com.cascading.demo.enums.ProjectDomainEnum;
import com.cascading.demo.model.Project;
import com.cascading.demo.repository.ProjectRepository;
import com.cascading.demo.service.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("Should save the project and return the saved project")
    void saveProjectAndReturnSavedProject() { // create a project object
        Project project = new Project();
        project.setId(1);
        project.setProjectName("Project 1");
        project.setDepartmentCode("D001");
        project.setDomain(ProjectDomainEnum.CYBERSECURITY);

        // mock the project repository's save method
        when(projectRepository.save(project)).thenReturn(project);

        // call the saveProject method of project service
        Project savedProject = projectService.saveProject(project);

        // verify that the project repository's save method was called once
        verify(projectRepository, times(1)).save(project);

        // assert that the saved project is not null
        assertNotNull(savedProject);

        // assert that the saved project is equal to the original project
        assertEquals(project, savedProject);
    }
}