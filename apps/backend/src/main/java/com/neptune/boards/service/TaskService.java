package com.neptune.boards.service;

import com.neptune.boards.dto.task.TaskRequestDTO;
import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.entity.Task;
import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.State;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.TaskMapper;
import com.neptune.boards.repository.ProjectStateRepository;
import com.neptune.boards.repository.StateRepository;
import com.neptune.boards.repository.TaskRepository;
import com.neptune.boards.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private ProjectStateRepository projectStateRepository;

    @Override
    public TaskResponseDTO getTask(UUID uuid) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        return TaskMapper.mapBoardToResponseDTO(task.get());
    }

    @Override
    public TaskResponseDTO createTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        Optional<Project> board = projectRepository.findByUUID(taskRequest.getBoard());
        Optional<State> state = stateRepository.findByUUID(taskRequest.getState());

        if (board.isEmpty()) {
            throw new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        if(state.isEmpty()){
            throw new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Optional<ProjectState> projectState = projectStateRepository.findByProjectAndState(board.get(), state.get());

        if(projectState.isEmpty())
            throw new NeptuneBoardsException("ProjectState not found", HttpStatus.NOT_FOUND, this.getClass());

        Task task = Task.builder()
                .UUID(uuid)
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .project(board.get())
                .state(projectState.get())
                .build();

        return TaskMapper.mapBoardToResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO updateTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Task updatedTask = task.get().updateFromDto(taskRequest);
        return TaskMapper.mapBoardToResponseDTO(taskRepository.save(updatedTask));
    }

    @Override
    public TaskResponseDTO deleteTask(UUID uuid) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        taskRepository.delete(task.get());
        return TaskMapper.mapBoardToResponseDTO(task.get());
    }

    @Override
    public List<TaskResponseDTO> listTasks() {
        return taskRepository.findAll().stream().map(TaskMapper::mapBoardToResponseDTO).toList();
    }
}
