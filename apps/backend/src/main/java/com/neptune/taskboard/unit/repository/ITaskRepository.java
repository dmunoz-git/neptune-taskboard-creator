package com.neptune.taskboard.unit.repository;

import com.neptune.taskboard.unit.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
