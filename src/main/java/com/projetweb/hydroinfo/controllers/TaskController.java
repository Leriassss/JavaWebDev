package com.projetweb.hydroinfo.controllers;

import com.projetweb.hydroinfo.dtos.TaskDto;
import com.projetweb.hydroinfo.models.Task;
import com.projetweb.hydroinfo.services.TaskService;
import com.projetweb.hydroinfo.services.impl.TaskServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<Task> createTask(@PathVariable Long projectId, @Valid @RequestBody TaskDto createTaskDto) {
        Task createdTask = taskService.createTask(projectId, createTaskDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }
    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public ResponseEntity<Task> updateTask(
            @PathVariable Long projectId,
            @PathVariable Long taskId,
            @Valid @RequestBody TaskDto updateTaskDto) {
        Task updatedTask = taskService.updateTask(projectId, taskId, updateTaskDto);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTask);
    }


    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.status(HttpStatus.OK).body("Suppression effectuée avec succès");
    }

    @GetMapping("/projects/{projectId}/tasks/overdue")
    public ResponseEntity<Page<Task>> getOverdueTasks(
            @PathVariable Long projectId,
            @RequestParam int page,
            @RequestParam int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Task> tasks = taskService.getOverdueTasks(projectId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(tasks);
    }

}
