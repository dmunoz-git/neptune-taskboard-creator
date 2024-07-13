package com.neptune.boards.service;

import com.neptune.boards.entity.Task;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;


public interface ITaskService {
    public Task getTask(Long id) throws NeptuneBoardsException;
    public Task create(Long dashboardId, Task task) throws NeptuneBoardsException;
    public Task update(Long id, Task task) throws NeptuneBoardsException;
    public Task delete(Long taskId) throws NeptuneBoardsException;
    public List<Task> listTasks();
}
