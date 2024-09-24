package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.state.StateRequestDTO;
import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.StateRepository;
import com.neptune.boards.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateServiceTest {

    @Mock
    private StateRepository repository;

    @InjectMocks
    private StateService service;

    private State state;
    private UUID stateUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stateUUID = UUID.randomUUID();
        state = State.builder()
                .UUID(stateUUID)
                .name("To Do")
                .definitionOfDone("Tasks that need to be started")
                .build();
    }

    @Test
    @DisplayName("Unit Test: Get State: should return state when found")
    void getStateSuccessTest() throws NeptuneBoardsException {
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.of(state));

        StateResponseDTO response = service.getState(stateUUID);

        assertNotNull(response);
        assertEquals(stateUUID, response.getUuid());
        assertEquals("To Do", response.getName());
        verify(repository, times(1)).findByUUID(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Get State: should throw exception if state not found")
    void getStateNotFoundTest() {
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.getState(stateUUID)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateService.class, exception.getOriginClass());
        verify(repository, times(1)).findByUUID(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Create State: should create state successfully")
    void createStateSuccessTest() {
        StateRequestDTO request = new StateRequestDTO("In Progress", "Tasks in progress");
        when(repository.save(any(State.class))).thenReturn(state);

        StateResponseDTO response = service.createState(stateUUID, request);

        assertNotNull(response);
        assertEquals(stateUUID, response.getUuid());
        assertEquals("To Do", response.getName());
        verify(repository, times(1)).save(any(State.class));
    }

    @Test
    @DisplayName("Unit Test: Delete State: should delete state when found")
    void deleteStateSuccessTest() throws NeptuneBoardsException {
        // Mock data
        UUID stateUUID = UUID.randomUUID();
        State state = new State(null, stateUUID, "To Do", "Tasks that need to be started");

        // Mock the repository behavior
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.of(state));

        // Call the service method
        StateResponseDTO response = service.deleteState(stateUUID);

        // Verify the response and interactions
        assertNotNull(response);
        assertEquals(stateUUID, response.getUuid());
    }

    @Test
    @DisplayName("Unit Test: Delete State: should throw exception if state not found")
    void deleteStateNotFoundTest() {
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.deleteState(stateUUID)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateService.class, exception.getOriginClass());
        verify(repository, times(1)).findByUUID(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Update State: should update state when found")
    void updateStateSuccessTest() throws NeptuneBoardsException {
        StateRequestDTO request = new StateRequestDTO("Done", "Tasks completed");
        State updatedState = state.updateFromDto(request);
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.of(state));
        when(repository.save(any(State.class))).thenReturn(updatedState);

        StateResponseDTO response = service.updateState(stateUUID, request);

        assertNotNull(response);
        assertEquals(stateUUID, response.getUuid());
        assertEquals("Done", response.getName());
        verify(repository, times(1)).findByUUID(stateUUID);
        verify(repository, times(1)).save(updatedState);
    }

    @Test
    @DisplayName("Unit Test: Update State: should throw exception if state not found")
    void updateStateNotFoundTest() {
        StateRequestDTO request = new StateRequestDTO("Done", "Tasks completed");
        when(repository.findByUUID(stateUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.updateState(stateUUID, request)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateService.class, exception.getOriginClass());
        verify(repository, times(1)).findByUUID(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: List States: should return list of states")
    void listStatesSuccessTest() {
        when(repository.findAll()).thenReturn(List.of(state));

        List<StateResponseDTO> states = service.listStates();

        assertNotNull(states);
        assertFalse(states.isEmpty());
        assertEquals(1, states.size());
        verify(repository, times(1)).findAll();
    }
}
