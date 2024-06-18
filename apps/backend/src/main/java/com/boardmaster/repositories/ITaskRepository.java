package com.boardmaster.repositories;

import com.boardmaster.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
