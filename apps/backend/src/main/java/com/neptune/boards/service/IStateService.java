package com.neptune.boards.service;

import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;

public interface IStateService {
    public State getState(Long id) throws NeptuneBoardsException;
    public State getState(String name) throws NeptuneBoardsException;
    public State create(State state);
    public State delete(Long columnId) throws NeptuneBoardsException;
    public void changeName(String name) throws NeptuneBoardsException;
    public void updateName(Long id, String name) throws NeptuneBoardsException;
}
