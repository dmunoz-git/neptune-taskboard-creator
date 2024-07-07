package com.neptune.taskboard.repository;

import com.neptune.taskboard.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
