package com.projetweb.hydroinfo.services.impl;


import com.projetweb.hydroinfo.dtos.ProjectDto;
import com.projetweb.hydroinfo.exceptions.CustomValidationException;
import com.projetweb.hydroinfo.exceptions.EntityNotFoundException;
import com.projetweb.hydroinfo.models.Project;
import com.projetweb.hydroinfo.repositories.ProjectRepository;
import com.projetweb.hydroinfo.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ProjectRepository projectRepository;

    @Override
    public Project createProject(ProjectDto createProjectDto) {
        List<String> errors = new ArrayList<>();

        if (createProjectDto.endedAt().isBefore(createProjectDto.startedAt())) {
            errors.add("La date de fin ne peut pas être antérieure à la date de début.");
        }
        if (projectRepository.existsByName(createProjectDto.name())) {
            errors.add("Un projet avec ce nom existe déjà.");
        }
        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        Project project = Project.builder()
                .name(createProjectDto.name())
                .description(createProjectDto.description())
                .startedAt(createProjectDto.startedAt())
                .endedAt(createProjectDto.endedAt())
                .build();

        return projectRepository.save(project);
    }


    @Override
    public Project getProject(Long id) {
        Optional<Project> project = projectRepository.findById(id);

        if (project.isEmpty()) {
            throw new EntityNotFoundException(
                    "Project",
                    "id",
                    id.toString()
            );
        }

        return project.get();
    }

    @Override
    public Project updateProject(Long id, ProjectDto createProjectDto) {
        Project  project = projectRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Project ", "id", id.toString()));

        List<String> errors = new ArrayList<>();

        if (createProjectDto.endedAt().isBefore(createProjectDto.startedAt())) {
            errors.add("La date de fin ne peut pas être antérieure à la date de début.");
        }
        if (projectRepository.existsByNameAndIdNot(createProjectDto.name(), id)) {
            errors.add("Un projet avec ce nom existe déjà.");
        }

        if (!errors.isEmpty()) {
            throw new CustomValidationException(errors);
        }
        project.setName(createProjectDto.name());
        project.setDescription(createProjectDto.description());
        project.setStartedAt(createProjectDto.startedAt());
        project.setEndedAt(createProjectDto.endedAt());

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long id) {
        Project  project = projectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Project ", "id", id.toString()));

        projectRepository.delete(project);

    }

    @Override
    public Page<Project> getProjectsPage(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }

}