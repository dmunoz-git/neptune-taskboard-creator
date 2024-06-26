package com.boardmaster.repository;

import com.boardmaster.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository extends JpaRepository<Task, Long> {
}
