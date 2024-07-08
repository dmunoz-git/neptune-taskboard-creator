package com.neptune.taskboard.unit.service;

import com.neptune.taskboard.unit.entity.State;
import com.neptune.taskboard.unit.exception.BoardmasterException;

public interface IStateService {
    public State getState(Long id) throws BoardmasterException;
    public State getState(String name) throws BoardmasterException;
    public State create(State state);
    public State delete(Long columnId) throws BoardmasterException;
    public void changeName(String name) throws BoardmasterException;
    public void updateName(Long id, String name) throws BoardmasterException;
}
