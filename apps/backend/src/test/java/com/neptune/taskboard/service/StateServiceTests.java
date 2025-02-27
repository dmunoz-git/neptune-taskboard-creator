package com.neptune.taskboard.service;

import com.neptune.taskboard.entity.State;
import com.neptune.taskboard.exception.BoardmasterException;
import com.neptune.taskboard.repository.IStateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class StateServiceTests {

    @Mock
    IStateRepository repository;

    @InjectMocks
    private StateService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get State by ID: should return state if found")
    void getStateByIdTest() throws BoardmasterException {
        State state = new State();
        state.setName("Test State");
        when(repository.findById(1L)).thenReturn(Optional.of(state));

        State foundState = service.getState(1L);

        assertNotNull(foundState);
        assertEquals("Test State", foundState.getName());
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get State by ID: should throw exception if not found")
    void getStateByIdNotFoundTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.getState(1L));
        verify(repository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get State by Name: should return state if found")
    void getStateByNameTest() throws BoardmasterException {
        State state = new State();
        state.setName("Test State");
        when(repository.findByName("Test State")).thenReturn(Optional.of(state));

        State foundState = service.getState("Test State");

        assertNotNull(foundState);
        assertEquals("Test State", foundState.getName());
        verify(repository, times(1)).findByName("Test State");
    }

    @Test
    @DisplayName("Get State by Name: should throw exception if not found")
    void getStateByNameNotFoundTest() {
        when(repository.findByName("Test State")).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.getState("Test State"));
        verify(repository, times(1)).findByName("Test State");
    }

    @Test
    @DisplayName("Create State: should create and return a new state")
    void createStateTest() {
        State state = new State();
        state.setName("New State");
        when(repository.save(any(State.class))).thenReturn(state);

        State createdState = service.create(state);

        assertNotNull(createdState);
        assertEquals("New State", createdState.getName());
        verify(repository, times(1)).save(any(State.class));
    }

    @Test
    @DisplayName("Delete State by ID: should delete state if found")
    void deleteStateByIdTest() throws BoardmasterException {
        State state = new State();
        state.setName("Test State");
        when(repository.findById(1L)).thenReturn(Optional.of(state));

        State deletedState = service.delete(1L);

        assertNotNull(deletedState);
        assertEquals("Test State", deletedState.getName());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(state);
    }

    @Test
    @DisplayName("Delete State by ID: should throw exception if not found")
    void deleteStateByIdNotFoundTest() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.delete(1L));
        verify(repository, times(1)).findById(1L);
        verify(repository, times(0)).delete(any(State.class));
    }

    @Test
    @DisplayName("Change State Name: should update the state name")
    void changeStateNameTest() throws BoardmasterException {
        State state = new State();
        state.setName("Old Name");
        when(repository.findByName("Old Name")).thenReturn(Optional.of(state));
        when(repository.save(any(State.class))).thenReturn(state);

        service.changeName("Old Name");

        assertEquals("Old Name", state.getName());
        verify(repository, times(1)).findByName("Old Name");
        verify(repository, times(1)).save(any(State.class));
    }

    @Test
    @DisplayName("Update State Name by ID: should update the state name")
    void updateStateNameByIdTest() throws BoardmasterException {
        State state = new State();
        state.setName("Old Name");
        when(repository.findById(1L)).thenReturn(Optional.of(state));
        when(repository.save(any(State.class))).thenReturn(state);

        service.updateName(1L, "New Name");

        assertEquals("New Name", state.getName());
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).save(any(State.class));
    }
}
