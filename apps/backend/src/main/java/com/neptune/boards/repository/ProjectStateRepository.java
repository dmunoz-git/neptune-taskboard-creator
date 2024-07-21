package com.neptune.boards.repository;

import com.neptune.boards.entity.ProjectState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectStateRepository extends JpaRepository<ProjectState, Long> {
    public Optional<ProjectState> findByUUID(UUID uuid);
}
