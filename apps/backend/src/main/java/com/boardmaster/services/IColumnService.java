package com.boardmaster.services;

import com.boardmaster.entities.State;
import org.springframework.scheduling.config.Task;

public interface IColumnService {
    public void create(State state);
    public void delete(Long columnId);
    public void changeName(String name);
    public void addTask(Task task);
}
