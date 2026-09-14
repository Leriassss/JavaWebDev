package com.projetweb.hydroinfo.repositories;

import com.projetweb.hydroinfo.models.Project;
import com.projetweb.hydroinfo.models.Task;
import com.projetweb.hydroinfo.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    boolean  existsByNameAndProjectId(String projectName, Long projectId);
    boolean  existsByNameAndProjectIdAndIdNot(String projectName, Long projectId, Long taskId);
    @Query("SELECT t FROM Task t WHERE t.project.id = :projectId AND t.endedAt < CURRENT_TIMESTAMP")
    Page<Task> findOverdueTasksByProjectId(@Param("projectId") Long projectId, Pageable pageable);

}
