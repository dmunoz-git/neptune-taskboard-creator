package com.neptune.taskboard.unit.service;

import com.neptune.taskboard.unit.entity.Task;
import com.neptune.taskboard.unit.entity.Dashboard;
import com.neptune.taskboard.unit.exception.BoardmasterException;
import com.neptune.taskboard.unit.repository.ITaskRepository;
import com.neptune.taskboard.unit.repository.IDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private ITaskRepository taskRepository;

    @Autowired
    private IDashboardRepository dashboardRepository;

    @Override
    public Task getTask(Long id) throws BoardmasterException {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isEmpty()) {
            throw new BoardmasterException("Task not found");
        }
        return task.get();
    }

    @Override
    public Task create(Long dashboardId, Task task) throws BoardmasterException {
        Optional<Dashboard> dashboard = dashboardRepository.findById(dashboardId);
        if (dashboard.isEmpty()) {
            throw new BoardmasterException("Dashboard not found");
        }
        task.setDashboard(dashboard.get());
        return taskRepository.save(task);
    }

    @Override
    public Task update(Long id, Task task) throws BoardmasterException {
        Task existingTask = getTask(id);
        existingTask.setName(task.getName());
        existingTask.setDescription(task.getDescription());
        existingTask.setState(task.getState());
        return taskRepository.save(existingTask);
    }

    @Override
    public Task delete(Long taskId) throws BoardmasterException {
        Task task = getTask(taskId);
        taskRepository.delete(task);
        return task;
    }

    @Override
    public List<Task> listTasks() {
        return taskRepository.findAll();
    }


}
