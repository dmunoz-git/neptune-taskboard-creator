package com.neptune.boards.service;

import com.neptune.boards.dto.StateRequestDTO;
import com.neptune.boards.dto.StateResponseDTO;
import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.StateMapper;
import com.neptune.boards.mapper.TaskMapper;
import com.neptune.boards.repository.StateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StateService implements IStateService{
    @Autowired
    StateRepository repository;

    @Override
    public StateResponseDTO getState(UUID uuid) throws NeptuneBoardsException {
        Optional<State> state = repository.findByUUID(uuid);

        if(state.isEmpty())
            throw new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, this.getClass());

        return StateMapper.mapStateToResponseDTO(state.get());
    }

    @Override
    public StateResponseDTO createState(UUID uuid, StateRequestDTO stateRequest) {
        State state = State.builder()
                .UUID(uuid)
                .name(stateRequest.getName())
                .dod(stateRequest.getDod())
                .build();

        return StateMapper.mapStateToResponseDTO(repository.save(state));
    }

    @Override
    public StateResponseDTO deleteState(UUID uuid) throws NeptuneBoardsException {
        Optional<State> state = repository.findByUUID(uuid);

        if(state.isEmpty())
            throw new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, this.getClass());

        return StateMapper.mapStateToResponseDTO(state.get());
    }

    @Override
    public StateResponseDTO updateState(UUID uuid, StateRequestDTO stateRequest) throws NeptuneBoardsException {
        Optional<State> state = repository.findByUUID(uuid);

        if(state.isEmpty())
            throw new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, this.getClass());

        State updatedState = state.get().updateFromDto(stateRequest);
        this.repository.save(updatedState);

        return StateMapper.mapStateToResponseDTO(updatedState);
    }

    @Override
    public List<StateResponseDTO> listStates() {
        List<State> states = repository.findAll();
        return states.stream().map(StateMapper::mapStateToResponseDTO).toList();
    }

}
