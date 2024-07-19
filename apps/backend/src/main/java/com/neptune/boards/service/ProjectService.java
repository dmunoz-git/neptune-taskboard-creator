package com.neptune.boards.service;

import com.neptune.boards.dto.ProjectRequestDTO;
import com.neptune.boards.dto.ProjectResponseDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.ProjectMapper;
import com.neptune.boards.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository repository;

    @Override
    public ProjectResponseDTO createBoard(UUID uuid, ProjectRequestDTO boardRequest) {
        // Create project object
        Project project = Project.builder()
                .UUID(uuid)
                .name(boardRequest.getName())
                .description(boardRequest.getDescription())
                .build();

        // Save and autogenerate the rest of values
        Project savedProject = this.repository.save(project);

        // Map database object to project response
        return ProjectMapper.mapBoardToResponseDTO(savedProject);
    }

    @Override
    public ProjectResponseDTO getBoard(UUID uuid) throws NeptuneBoardsException {
        Optional<Project> board = repository.findByUUID(uuid);

        if(board.isEmpty()){
            throw new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Project savedProject = board.get();

        return ProjectMapper.mapBoardToResponseDTO(savedProject);
    }

    @Override
    public List<ProjectResponseDTO> getAllBoards() {
        return repository.findAll().stream().map(ProjectMapper::mapBoardToResponseDTO).toList();
    }

    @Override
    public ProjectResponseDTO deleteBoard(UUID uuid) throws NeptuneBoardsException {
        Optional<Project> board = repository.findByUUID(uuid);

        if(board.isEmpty()){
            throw new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        this.repository.delete(board.get());

        return ProjectMapper.mapBoardToResponseDTO(board.get());
    }


    @Override
    public ProjectResponseDTO updateBoard(UUID uuid, ProjectRequestDTO boardRequest) throws NeptuneBoardsException {
        Optional<Project> foundBoard = repository.findByUUID(uuid);

        if(foundBoard.isEmpty()){
            throw new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Project updatedProject = foundBoard.get().updateFromDto(boardRequest);

        // Save updated project
        Project savedProject = repository.save(updatedProject);

        return ProjectMapper.mapBoardToResponseDTO(savedProject);
    }
}
