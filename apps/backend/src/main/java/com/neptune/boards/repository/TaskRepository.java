package com.neptune.boards.repository;

import com.neptune.boards.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, Long> {
    public Optional<Task> findByUUID(UUID uuid);
}
