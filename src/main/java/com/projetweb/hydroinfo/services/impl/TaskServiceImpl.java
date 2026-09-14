package com.projetweb.hydroinfo.services.impl;

import com.projetweb.hydroinfo.dtos.TaskDto;
import com.projetweb.hydroinfo.exceptions.CustomValidationException;
import com.projetweb.hydroinfo.exceptions.EntityNotFoundException;
import com.projetweb.hydroinfo.models.Project;
import com.projetweb.hydroinfo.models.Task;
import com.projetweb.hydroinfo.models.TaskStatus;
import com.projetweb.hydroinfo.models.User;
import com.projetweb.hydroinfo.repositories.ProjectRepository;
import com.projetweb.hydroinfo.repositories.TaskRepository;
import com.projetweb.hydroinfo.repositories.UserRepository;
import com.projetweb.hydroinfo.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    //Créer un tâche
    @Override
    public Task createTask(Long projectId, TaskDto createTaskDto) {
        List<String> validationErrors = new ArrayList<>();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", "id", projectId.toString()));

        boolean exists = taskRepository.existsByNameAndProjectId(createTaskDto.name(), projectId);
        if (exists) {
            validationErrors.add("Une tâche avec ce nom existe déjà dans ce projet.");
        }

        if (createTaskDto.startedAt().isBefore(project.getStartedAt()) ||
                createTaskDto.endedAt().isAfter(project.getEndedAt()) ||
                createTaskDto.startedAt().isAfter(createTaskDto.endedAt())) {
            validationErrors.add("Les dates de la tâche ne respectent pas les contraintes du projet.");
        }
        if (!validationErrors.isEmpty()) {
            throw new CustomValidationException(validationErrors);
        }
        User user = userRepository.findById(createTaskDto.userId())
                .orElseThrow(() -> new EntityNotFoundException("User", "id", createTaskDto.userId().toString()));


        Task task = Task.builder()
                .name(createTaskDto.name())
                .description(createTaskDto.description())
                .startedAt(createTaskDto.startedAt())
                .endedAt(createTaskDto.endedAt())
                .project(project)
                .user(user)
                .status(TaskStatus.TO_DO)
                .build();

        return taskRepository.save(task);
    }


    // Mettre à jour une tâche relative à un projet
    @Override
    public Task updateTask(Long projectId, Long taskId, TaskDto updateTaskDto) {
        List<String> validationErrors = new ArrayList<>();
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", "id", projectId.toString()));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Task", "id", taskId.toString()));

        boolean exists = taskRepository.existsByNameAndProjectIdAndIdNot(updateTaskDto.name(), projectId, taskId);
        if (exists) {
            validationErrors.add("Une tâche avec ce nom existe déjà dans ce projet");
        }

        if (updateTaskDto.startedAt().isBefore(project.getStartedAt()) ||
                updateTaskDto.endedAt().isAfter(project.getEndedAt()) ||
                updateTaskDto.startedAt().isAfter(updateTaskDto.endedAt())) {
            validationErrors.add("Les dates de la tâche ne respectent pas les contraintes du projet");
        }
        String currentStatus = task.getStatus();
        String newStatus = updateTaskDto.status();

        if(updateTaskDto.status() == null){
            validationErrors.add("Le champs statut est manquant");
        }
        else if (!isValidStatusTransition(currentStatus, newStatus)) {
            validationErrors.add("Ce changement de statut de la tâche n'est pas autorisé");
        }
        if (!validationErrors.isEmpty()) {
            throw new CustomValidationException(validationErrors);
        }
        task.setName(updateTaskDto.name());
        task.setDescription(updateTaskDto.description());
        task.setStartedAt(updateTaskDto.startedAt());
        task.setEndedAt(updateTaskDto.endedAt());
        task.setStatus(newStatus);

        return taskRepository.save(task);
    }


    // Supprimer une tâche
    @Override
    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);

        if (task.isEmpty()) {
            throw new EntityNotFoundException("Task", "id", id.toString());
        }
        taskRepository.delete(task.get());
    }

    // Liste des tâches en cours et échues
    @Override
    public Page<Task> getOverdueTasks(Long projectId, Pageable pageable) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new EntityNotFoundException("Project", "id", projectId.toString()));

        return taskRepository.findOverdueTasksByProjectId(project.getId(), pageable);
    }


    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        return (currentStatus.equals(TaskStatus.TO_DO) && newStatus.equals(TaskStatus.IN_PROGRESS)) ||
                (currentStatus.equals(TaskStatus.IN_PROGRESS) && newStatus.equals(TaskStatus.DONE)) ||
                (currentStatus.equals(TaskStatus.DONE) && newStatus.equals(TaskStatus.IN_PROGRESS)) ||
                (currentStatus.equals(TaskStatus.IN_PROGRESS) && newStatus.equals(TaskStatus.TO_DO));
    }
}
