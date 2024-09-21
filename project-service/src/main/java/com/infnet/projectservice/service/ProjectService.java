package com.infnet.projectservice.service;

import com.infnet.projectservice.model.Project;
import com.infnet.projectservice.model.Task;

import java.util.List;
import java.util.Optional;

public interface ProjectService {
    List<Project> findAll() throws Exception;

    Optional<Project> findById(Long id) throws Exception;

    List<Project> findByName(String name) throws Exception;

    Project create(Project project) throws Exception;

    Optional<Project> update(Long id, Project project) throws Exception;

    Optional<Project> delete(Long id) throws Exception;

    Optional<Project> addRelatedProject(Long id, Long relatedProjectId) throws Exception;

    void addTask(Long id, Task task) throws Exception;
}
