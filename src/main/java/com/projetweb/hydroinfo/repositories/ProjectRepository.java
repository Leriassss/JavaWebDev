package com.projetweb.hydroinfo.repositories;

import com.projetweb.hydroinfo.models.Project;
import com.projetweb.hydroinfo.models.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    boolean existsByName(String name);
    boolean  existsByNameAndIdNot(String projectName, Long projectId);
}
