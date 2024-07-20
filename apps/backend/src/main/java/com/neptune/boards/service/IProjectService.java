package com.neptune.boards.service;

import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;

public interface IProjectService {
    public ProjectResponseDTO createBoard(UUID uuid, ProjectRequestDTO requestDTO);
    public ProjectResponseDTO getBoard(UUID uuid) throws NeptuneBoardsException;
    public List<ProjectResponseDTO> getAllBoards();
    public ProjectResponseDTO deleteBoard(UUID uuid) throws NeptuneBoardsException;
    public ProjectResponseDTO updateBoard(UUID uuid, ProjectRequestDTO dto) throws NeptuneBoardsException;
}
