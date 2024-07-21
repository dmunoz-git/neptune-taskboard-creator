package com.neptune.boards.service;

import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.ProjectStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectStateService implements IProjectStateService{
    @Autowired
    ProjectStateRepository repository;

    @Override
    public ProjectState createProjectState(ProjectState projectState) {
        return repository.save(projectState);
    }

    @Override
    public ProjectState removeProjectState(UUID uuid) throws NeptuneBoardsException {
        ProjectState foundProjectState = this.getProjectState(uuid);

        this.repository.delete(foundProjectState);

        return foundProjectState;
    }

    @Override
    public List<ProjectState> listProjectState() {
        return repository.findAll();
    }

    @Override
    public ProjectState getProjectState(UUID uuid) throws NeptuneBoardsException {
        Optional<ProjectState> foundProjectState = repository.findByUUID(uuid);

        if(foundProjectState.isEmpty())
            throw new NeptuneBoardsException("ProjectState not found", HttpStatus.NOT_FOUND, this.getClass());

        return foundProjectState.get();
    }
}
