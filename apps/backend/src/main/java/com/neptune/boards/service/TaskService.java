package com.neptune.boards.service;

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

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private BoardRepository dashboardRepository;

    @Override
    public Task getTask(Long id) throws NeptuneBoardsException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass());
        }
        return task.get();
    }

    @Override
    public Task create(Long dashboardId, Task task) throws NeptuneBoardsException {
        Optional<Board> dashboard = dashboardRepository.findById(dashboardId);
        if (dashboard.isEmpty()) {
            throw new NeptuneBoardsException("Dashboard not found", HttpStatus.NOT_FOUND, this.getClass());
        }
        task.setBoard(dashboard.get());
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task) throws NeptuneBoardsException {
        Task existingTask = getTask(id);
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setState(task.getState());
        return taskRepository.save(existingTask);
    }

    @Override
    public Task delete(Long taskId) throws NeptuneBoardsException {
        Task task = getTask(taskId);
        taskRepository.delete(task);
        return task;
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }


}
