package com.neptune.boards.service;

import com.neptune.boards.dto.task.TaskRequestDTO;
import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;


public interface ITaskService {
    public TaskResponseDTO getTask(UUID uuid) throws NeptuneBoardsException;
    public TaskResponseDTO createTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException;
    public TaskResponseDTO updateTask(UUID uuid, TaskRequestDTO taskRequest) throws NeptuneBoardsException;
    public TaskResponseDTO deleteTask(UUID uuid) throws NeptuneBoardsException;
    public List<TaskResponseDTO> listTasks();
}
