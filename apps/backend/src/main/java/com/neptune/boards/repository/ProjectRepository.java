package com.neptune.boards.repository;

import com.neptune.boards.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    public Optional<Project> findByUUID(UUID uuid);
    public void deleteByUUID(UUID uuid);
}
