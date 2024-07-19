package com.neptune.boards.service;

import com.neptune.boards.dto.state.StateRequestDTO;
import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;

public interface IStateService {
    public StateResponseDTO getState(UUID uuid) throws NeptuneBoardsException;
    public StateResponseDTO createState(UUID uuid, StateRequestDTO stateRequest);
    public StateResponseDTO deleteState(UUID uuid) throws NeptuneBoardsException;
    public StateResponseDTO updateState(UUID uuid, StateRequestDTO stateRequest) throws NeptuneBoardsException;
    public List<StateResponseDTO> listStates();
}
