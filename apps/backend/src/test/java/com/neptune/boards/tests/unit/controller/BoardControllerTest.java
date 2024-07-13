package com.neptune.boards.tests.unit.controller;

import com.neptune.boards.controller.BoardController;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.BoardmasterException;
import com.neptune.boards.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService service;

    private Board board;

    @BeforeEach
    void setUp() {
        this.board =Board.builder().name("Test board").build();
    }

    @Test
    @DisplayName("Create a Board: should return the created board")
    public void testCreateController() throws Exception {
        Mockito.when(service.createBoard(board)).thenReturn(this.board);

        mockMvc.perform(post("/api/dashboard?name=Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return dashboard if found")
    public void testGetDashboardById() throws Exception {
        Mockito.when(service.getBoard(UUID.randomUUID())).thenReturn(this.board);

        mockMvc.perform(get("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return not found if dashboard does not exist")
    public void testGetDashboardByIdNotFound() throws Exception {
        Mockito.when(service.getBoard(UUID.randomUUID())).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(get("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Dashboard by Name: should return dashboard if found")
    public void testGetDashboardByName() throws Exception {
        Mockito.when(service.getBoard(UUID.randomUUID())).thenReturn(this.board);

        mockMvc.perform(get("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by Name: should return not found if dashboard does not exist")
    public void testGetDashboardByNameNotFound() throws Exception {
        Mockito.when(service.getBoard(UUID.randomUUID())).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(get("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get All Dashboards: should return list of dashboards")
    public void testGetAllDashboards() throws Exception {
        List<Board> dashboards = Arrays.asList(Board.builder().name("Dashboard1").build(), Board.builder().name("Dashboard2").build());
        Mockito.when(service.getAllBoards()).thenReturn(dashboards);

        mockMvc.perform(get("/api/dashboard/all")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Dashboard1"))
                .andExpect(jsonPath("$[1].name").value("Dashboard2"));
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should delete dashboard if found")
    public void testDeleteDashboardById() throws Exception {
        Mockito.when(service.deleteBoard(UUID.randomUUID())).thenReturn(this.board);

        mockMvc.perform(delete("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should return not found if dashboard does not exist")
    public void testDeleteDashboardByIdNotFound() throws Exception {
        Mockito.when(service.deleteBoard(UUID.randomUUID())).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(delete("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should return not found if dashboard does not exist")
    public void testDeleteDashboardByNameNotFound() throws Exception {
        Mockito.when(service.deleteBoard(UUID.randomUUID())).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(delete("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
