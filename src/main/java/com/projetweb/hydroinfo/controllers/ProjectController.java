package com.projetweb.hydroinfo.controllers;

import com.projetweb.hydroinfo.dtos.ProjectDto;
import com.projetweb.hydroinfo.models.Project;
import com.projetweb.hydroinfo.services.ProjectService;
import com.projetweb.hydroinfo.services.impl.ProjectServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    //Créer un projet
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectDto createProjectDto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(createProjectDto));
    }

    //Details d'un projet
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProject(@PathVariable("id") Long projectId) {
        return ResponseEntity.status(HttpStatus.OK).body(projectService.getProject(projectId));
    }


    // Mettre à jour un projet
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody ProjectDto createProjectDto
    ) {
        Project updatedProject = projectService.updateProject(id, createProjectDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProject);
    }


    // Suppression d'un projet
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.status(HttpStatus.OK).body("Projet supprimé avec succès.");
    }

    // Liste des projets
    @GetMapping
    public ResponseEntity<Page<Project>> getProjectsByPage(
            @RequestParam int page,
            @RequestParam int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("id")));
        Page<Project> projects = projectService.getProjectsPage(pageable);

        return ResponseEntity.status(HttpStatus.OK).body(projects);
    }

}
