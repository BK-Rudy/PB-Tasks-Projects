package com.infnet.taskservice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.infnet.taskservice.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    List<Task> findAll() throws Exception;

    Optional<Task> findById(Long id) throws Exception;

    List<Task> findByName(String name) throws Exception;

    Task create(Task task) throws JsonProcessingException;

    Optional<Task> update(Long id, Task task) throws Exception;

    Optional<Task> delete(Long id) throws Exception;
}
