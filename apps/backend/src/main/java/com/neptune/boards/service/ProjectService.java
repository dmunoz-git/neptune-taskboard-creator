package com.neptune.boards.service;

import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.dto.projectState.TaskStateDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.ProjectMapper;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.repository.ProjectStateRepository;
import com.neptune.boards.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProjectService implements IProjectService {
    @Autowired
    private ProjectRepository repository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ProjectStateRepository projectStateRepository;

    @Override
    public ProjectResponseDTO createBoard(UUID uuid, ProjectRequestDTO boardRequest) {
        // Create default states
        List<State> defaultStates = this.createDefaultStates(boardRequest.getAllowedTaskStates());

        // Create project object
        Project project = Project.builder()
                .UUID(uuid)
                .name(boardRequest.getName())
                .description(boardRequest.getDescription())
                .build();

        // Create state associations and add to project
        TaskStateDTO defaultState = boardRequest.getAllowedTaskStates().stream().filter(TaskStateDTO::getDefaultState).findFirst().orElse(null);
        List<ProjectState> associations = this.createStatesAssociations(uuid, defaultStates, defaultState != null ? stateRepository.findByName(defaultState.getName()).get().getUUID() : defaultStates.get(0).getUUID());
        project.setTaskStates(associations);

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

    private List<State> createDefaultStates(List<TaskStateDTO> taskStates){
        List<State> projectStates = new ArrayList<>();

        for(TaskStateDTO taskState: taskStates) {
            Optional<State> presentState = stateRepository.findByName(taskState.getName());
            if(presentState.isEmpty()){
                State newState = State.builder()
                        .UUID(taskState.getStateUUID())
                        .name(taskState.getName())
                        .dod(taskState.dod)
                        .build();

                stateRepository.save(newState);

                projectStates.add(newState);
            } else
                projectStates.add(presentState.get());
        }

        return projectStates;
    }

    private List<ProjectState> createStatesAssociations(UUID projectUUID, List<State> states, UUID defaultState) {
        List<ProjectState> projectStates = new ArrayList<>();
        Optional<Project> project = repository.findByUUID(projectUUID);

        for(State state: states){
            ProjectState projectState = ProjectState.builder()
                    .UUID(UUID.randomUUID())
                    .project(project.get())
                    .state(state)
                    .defaultState(state.getUUID().equals(defaultState))
                    .build();

            projectStateRepository.save(projectState);
            projectStates.add(projectState);
        }

        return projectStates;
    }
}
