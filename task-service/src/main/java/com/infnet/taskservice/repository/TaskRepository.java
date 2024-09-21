package com.infnet.taskservice.repository;

import com.infnet.taskservice.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT u FROM Task u WHERE u.name = :name")
    List<Task> findByName(@Param("name") String name);
}
