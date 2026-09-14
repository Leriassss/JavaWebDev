package com.projetweb.hydroinfo.services;

import com.projetweb.hydroinfo.dtos.ProjectDto;
import com.projetweb.hydroinfo.models.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    Project createProject(ProjectDto createProjectDto);


    Project getProject(Long id);


    Project updateProject(Long id, ProjectDto createProjectDto);

    void deleteProject(Long id);

    Page<Project> getProjectsPage(Pageable pageable);

}
