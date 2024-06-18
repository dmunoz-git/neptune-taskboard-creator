package com.boardmaster.services;

import com.boardmaster.entities.Task;
import com.boardmaster.exceptions.BoardmasterException;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ITaskService {
    public Task getTask(Long id) throws BoardmasterException;
    public Task create(Long dashboardId, Task task) throws BoardmasterException;
    public Task update(Long id, Task task) throws BoardmasterException;
    public Task delete(Long taskId) throws BoardmasterException;
    public List<Task> listTasks();
}
