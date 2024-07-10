package com.neptune.taskboard.test.unit.controller;


import com.neptune.taskboard.controller.BoardController;
import com.neptune.taskboard.entity.Board;
import com.neptune.taskboard.exception.BoardmasterException;
import com.neptune.taskboard.service.BoardService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService service;

    private Board dashboard;

    @BeforeEach
    void setUp() {
        this.dashboard = new Board("Name");
    }

    @Test
    @DisplayName("Create a dashboard: should return created")
    public void testCreateController() throws Exception {
        Mockito.when(service.create("Name")).thenReturn(this.dashboard);

        mockMvc.perform(post("/api/dashboard?name=Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return dashboard if found")
    public void testGetDashboardById() throws Exception {
        Mockito.when(service.getDashboard(1L)).thenReturn(this.dashboard);

        mockMvc.perform(get("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by ID: should return not found if dashboard does not exist")
    public void testGetDashboardByIdNotFound() throws Exception {
        Mockito.when(service.getDashboard(1L)).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(get("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Dashboard by Name: should return dashboard if found")
    public void testGetDashboardByName() throws Exception {
        Mockito.when(service.getDashboard("Name")).thenReturn(this.dashboard);

        mockMvc.perform(get("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Get Dashboard by Name: should return not found if dashboard does not exist")
    public void testGetDashboardByNameNotFound() throws Exception {
        Mockito.when(service.getDashboard("Name")).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(get("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get All Dashboards: should return list of dashboards")
    public void testGetAllDashboards() throws Exception {
        List<Board> dashboards = Arrays.asList(new Board("Dashboard1"), new Board("Dashboard2"));
        Mockito.when(service.getAllDashboards()).thenReturn(dashboards);

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
        Mockito.when(service.deleteDashboard(1L)).thenReturn(this.dashboard);

        mockMvc.perform(delete("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Delete Dashboard by ID: should return not found if dashboard does not exist")
    public void testDeleteDashboardByIdNotFound() throws Exception {
        Mockito.when(service.deleteDashboard(1L)).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(delete("/api/dashboard/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should delete dashboard if found")
    public void testDeleteDashboardByName() throws Exception {
        Mockito.when(service.deleteDashboard("Name")).thenReturn(this.dashboard);

        mockMvc.perform(delete("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }

    @Test
    @DisplayName("Delete Dashboard by Name: should return not found if dashboard does not exist")
    public void testDeleteDashboardByNameNotFound() throws Exception {
        Mockito.when(service.deleteDashboard("Name")).thenThrow(new BoardmasterException("Dashboard not found"));

        mockMvc.perform(delete("/api/dashboard")
                        .param("name", "Name")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Change Dashboard Name: should update the dashboard name")
    public void testChangeDashboardName() throws Exception {
        Mockito.when(service.changeDashboardName("NewName")).thenReturn(this.dashboard);

        mockMvc.perform(put("/api/dashboard")
                        .param("currentName", "Name")
                        .param("newName", "NewName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Name"));
    }
}
