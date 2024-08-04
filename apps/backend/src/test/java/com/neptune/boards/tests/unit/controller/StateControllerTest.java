package com.neptune.boards.tests.unit.controller;

import com.neptune.boards.controller.StateController;
import com.neptune.boards.dto.state.StateRequestDTO;
import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StateControllerTest {

    @Mock
    private StateService service;

    @InjectMocks
    private StateController controller;

    private StateRequestDTO stateRequest;
    private StateResponseDTO stateResponse;
    private UUID stateUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        stateUUID = UUID.randomUUID();
        stateRequest = new StateRequestDTO("Test State", "Test Description");
        stateResponse = new StateResponseDTO(stateUUID, "Test State", "Test Description");
    }

    @Test
    @DisplayName("Unit Test: Get State by UUID: should return state when found")
    void getStateByUUIDSuccessTest() throws NeptuneBoardsException {
        when(service.getState(any(UUID.class))).thenReturn(stateResponse);

        ResponseEntity<StateResponseDTO> response = controller.getStateByUUID(stateUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stateResponse, response.getBody());
        verify(service, times(1)).getState(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Get State by UUID: should throw exception if state not found")
    void getStateByUUIDNotFoundTest() throws NeptuneBoardsException {
        when(service.getState(any(UUID.class))).thenThrow(new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, StateController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.getStateByUUID(stateUUID)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateController.class, exception.getOriginClass());
        verify(service, times(1)).getState(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Create State: should create state successfully")
    void createStateSuccessTest() {
        when(service.createState(any(UUID.class), any(StateRequestDTO.class))).thenReturn(stateResponse);

        ResponseEntity<StateResponseDTO> response = controller.createState(stateUUID, stateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(stateResponse, response.getBody());
        verify(service, times(1)).createState(stateUUID, stateRequest);
    }

    @Test
    @DisplayName("Unit Test: Delete State: should delete state successfully")
    void deleteStateSuccessTest() throws NeptuneBoardsException {
        when(service.deleteState(any(UUID.class))).thenReturn(stateResponse);

        ResponseEntity<StateResponseDTO> response = controller.deleteState(stateUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stateResponse, response.getBody());
        verify(service, times(1)).deleteState(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Delete State: should throw exception if state not found")
    void deleteStateNotFoundTest() throws NeptuneBoardsException {
        when(service.deleteState(any(UUID.class))).thenThrow(new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, StateController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.deleteState(stateUUID)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateController.class, exception.getOriginClass());
        verify(service, times(1)).deleteState(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Update State: should update state successfully")
    void updateStateSuccessTest() throws NeptuneBoardsException {
        when(service.updateState(any(UUID.class), any(StateRequestDTO.class))).thenReturn(stateResponse);

        ResponseEntity<StateResponseDTO> response = controller.updateState(stateUUID, stateRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(stateResponse, response.getBody());
        verify(service, times(1)).updateState(stateUUID, stateRequest);
    }

    @Test
    @DisplayName("Unit Test: Update State: should throw exception if state not found")
    void updateStateNotFoundTest() throws NeptuneBoardsException {
        when(service.updateState(any(UUID.class), any(StateRequestDTO.class))).thenThrow(new NeptuneBoardsException("State not found", HttpStatus.NOT_FOUND, StateController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.updateState(stateUUID, stateRequest)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(StateController.class, exception.getOriginClass());
        verify(service, times(1)).updateState(stateUUID, stateRequest);
    }

    @Test
    @DisplayName("Unit Test: List States: should return list of states")
    void listStatesSuccessTest() {
        when(service.listStates()).thenReturn(List.of(stateResponse));

        ResponseEntity<List<StateResponseDTO>> response = controller.listStates();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).listStates();
    }
}
