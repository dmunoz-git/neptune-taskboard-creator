package com.neptune.boards.service;

import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.entity.Task;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.TaskRepository;
import com.neptune.boards.repository.BoardRepository;
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
    private BoardRepository dashboardRepository;

    @Override
    public Task getTask(UUID uuid) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        return task.get();
    }

    @Override
    public Task createTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        Optional<Board> board = dashboardRepository.findByUUID(taskRequest.getBoard());

        if (board.isEmpty()) {
            throw new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Task task = Task.builder()
                .name(taskRequest.getName())
                .description(taskRequest.getDescription())
                .board(board.get())
                .build();

        return taskRepository.save(task);
    }

    @Override
    public Task updateTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Task updatedTask = task.get().updateFromDto(taskRequest);
        return taskRepository.save(updatedTask);
    }

    @Override
    public Task deleteTask(UUID uuid) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findByUUID(uuid);

        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        taskRepository.delete(task.get());
        return task.get();
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }


}
