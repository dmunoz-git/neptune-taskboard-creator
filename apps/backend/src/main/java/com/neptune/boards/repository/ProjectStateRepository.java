package com.neptune.boards.repository;

import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProjectStateRepository extends JpaRepository<ProjectState, Long> {
    public Optional<ProjectState> findByUUID(UUID uuid);
    public Optional<ProjectState> findByProjectAndState(Project project, State state);
}
