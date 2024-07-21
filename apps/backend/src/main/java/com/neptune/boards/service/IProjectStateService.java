package com.neptune.boards.service;

import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;

public interface IProjectStateService {
    public ProjectState createProjectState(ProjectState projectState);
    public ProjectState removeProjectState(UUID uuid) throws NeptuneBoardsException;
    public List<ProjectState> listProjectState();
    public ProjectState getProjectState(UUID uuid) throws NeptuneBoardsException;
}
