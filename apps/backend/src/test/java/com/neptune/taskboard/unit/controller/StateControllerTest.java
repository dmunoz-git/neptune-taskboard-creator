package com.neptune.taskboard.unit.controller;

import com.neptune.taskboard.unit.entity.State;
import com.neptune.taskboard.unit.exception.BoardmasterException;
import com.neptune.taskboard.unit.service.StateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StateController.class)
class StateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StateService service;

    private State state;

    @BeforeEach
    void setUp() {
        this.state = new State();
        this.state.setName("Test State");
    }

    @Test
    @DisplayName("Get State by ID: should return state if found")
    public void testGetStateById() throws Exception {
        Mockito.when(service.getState(1L)).thenReturn(this.state);

        mockMvc.perform(get("/state/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test State"));
    }

    @Test
    @DisplayName("Get State by ID: should return not found if state does not exist")
    public void testGetStateByIdNotFound() throws Exception {
        Mockito.when(service.getState(1L)).thenThrow(new BoardmasterException("State not found"));

        mockMvc.perform(get("/state/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get State by Name: should return state if found")
    public void testGetStateByName() throws Exception {
        Mockito.when(service.getState("Test State")).thenReturn(this.state);

        mockMvc.perform(get("/state")
                        .param("name", "Test State")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test State"));
    }

    @Test
    @DisplayName("Get State by Name: should return not found if state does not exist")
    public void testGetStateByNameNotFound() throws Exception {
        Mockito.when(service.getState("Test State")).thenThrow(new BoardmasterException("State not found"));

        mockMvc.perform(get("/state")
                        .param("name", "Test State")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create State: should create and return a new state")
    public void testCreateState() throws Exception {
        Mockito.when(service.create(Mockito.any(State.class))).thenReturn(this.state);

        mockMvc.perform(post("/state")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test State\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test State"));
    }

    @Test
    @DisplayName("Delete State by ID: should delete state if found")
    public void testDeleteStateById() throws Exception {
        Mockito.when(service.delete(1L)).thenReturn(this.state);

        mockMvc.perform(delete("/state/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test State"));
    }

    @Test
    @DisplayName("Delete State by ID: should return not found if state does not exist")
    public void testDeleteStateByIdNotFound() throws Exception {
        Mockito.when(service.delete(1L)).thenThrow(new BoardmasterException("State not found"));

        mockMvc.perform(delete("/state/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Change State Name: should update the state name")
    public void testChangeStateName() throws Exception {
        Mockito.doNothing().when(service).changeName("New State");

        mockMvc.perform(put("/state")
                        .param("currentName", "Test State")
                        .param("newName", "New State")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Update State Name by ID: should update the state name")
    public void testUpdateStateNameById() throws Exception {
        Mockito.doNothing().when(service).updateName(1L, "New State");

        mockMvc.perform(put("/state/1")
                        .param("name", "New State")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
