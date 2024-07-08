package com.neptune.taskboard.unit.service;

import com.neptune.taskboard.unit.entity.State;
import com.neptune.taskboard.unit.exception.BoardmasterException;
import com.neptune.taskboard.unit.repository.IStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StateService implements IStateService{
    @Autowired
    IStateRepository repository;

    @Override
    public State getState(Long id) throws BoardmasterException {
        Optional<State> state = repository.findById(id);

        if(state.isEmpty())
            throw new BoardmasterException("State not found");

        return state.get();
    }

    @Override
    public State getState(String name) throws BoardmasterException {
        Optional<State> state = repository.findByName(name);

        if(state.isEmpty())
            throw new BoardmasterException("State not found");

        return state.get();
    }

    @Override
    public State create(State state) {
        return repository.save(state);
    }

    @Override
    public State delete(Long id) throws BoardmasterException {
        State state = this.getState(id);

        this.repository.delete(state);

        return state;
    }

    @Override
    public void changeName(String name) throws BoardmasterException {
        State state = this.getState(name);
        state.setName(name);
        this.repository.save(state);
    }

    @Override
    public void updateName(Long id, String name) throws BoardmasterException {
        State state = this.getState(id);
        state.setName(name);
        this.repository.save(state);
    }

}
