package com.neptune.taskboard.service;

import com.neptune.taskboard.entity.Task;
import com.neptune.taskboard.exception.BoardmasterException;

import java.util.List;


public interface ITaskService {
    public Task getTask(Long id) throws BoardmasterException;
    public Task create(Long dashboardId, Task task) throws BoardmasterException;
    public Task update(Long id, Task task) throws BoardmasterException;
    public Task delete(Long taskId) throws BoardmasterException;
    public List<Task> listTasks();
}
