package com.neptune.boards.service;

import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.entity.Task;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;


public interface ITaskService {
    public Task getTask(UUID uuid) throws NeptuneBoardsException;
    public Task createTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException;
    public Task updateTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException;
    public Task deleteTask(UUID uuid) throws NeptuneBoardsException;
    public List<Task> listTasks();
}
