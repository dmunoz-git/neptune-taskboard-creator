package com.neptune.boards.service;

import com.neptune.boards.dto.task.TaskRequestDTO;
import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.entity.Task;
import com.neptune.boards.entity.Project;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.TaskMapper;
import com.neptune.boards.repository.TaskRepository;
import com.neptune.boards.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

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

        if (board.isEmpty()) {
            throw new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Task task = Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .project(board.get())
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
