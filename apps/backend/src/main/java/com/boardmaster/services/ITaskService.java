package com.boardmaster.services;

import com.boardmaster.entities.Task;
import org.springframework.stereotype.Service;


public interface ITaskService {
    public void create(Task task);
    public void update(Task task);
    public void delete(Long taskId);
}
