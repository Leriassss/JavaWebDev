package com.projetweb.hydroinfo.services;

import com.projetweb.hydroinfo.dtos.TaskDto;
import com.projetweb.hydroinfo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    Task createTask(Long projectId, TaskDto createTaskDto);

    Task updateTask(Long projectId, Long taskId, TaskDto updateTaskDto);

    void deleteTask(Long id);

    Page<Task> getOverdueTasks(Long projectId, Pageable pageable);
}
