package com.neptune.boards.tests.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptune.boards.controller.TaskController;
import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.dto.TaskResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private TaskResponseDTO taskResponse;
    private TaskRequestDTO taskRequest;
    private UUID taskUUID;
    private UUID boardUUID;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        this.taskUUID = UUID.randomUUID();
        this.boardUUID = UUID.randomUUID();

        this.taskRequest = TaskRequestDTO.builder()
                .name("Test Task")
                .description("Test Description")
                .board(boardUUID)
                .build();

        this.taskResponse = TaskResponseDTO.builder()
                .name("Test Task")
                .description("Test Description")
                .board(boardUUID)
                .build();
    }

    @Test
    @DisplayName("Get Task by UUID: should return task if found")
    public void testGetTaskById() throws Exception {
        Mockito.when(taskService.getTask(taskUUID)).thenReturn(this.taskResponse);

        mockMvc.perform(get("/api/task/{uuid}", taskUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Get Task by UUID: should return not found if task does not exist")
    public void testGetTaskByIdNotFound() throws Exception {
        Mockito.when(taskService.getTask(taskUUID)).thenThrow(new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(get("/api/task/{uuid}", taskUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create Task: should create and return a new task")
    public void testCreateTask() throws Exception {
        Mockito.when(taskService.createTask(boardUUID, taskRequest)).thenReturn(this.taskResponse);

        mockMvc.perform(post("/api/task/{uuid}", boardUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Update Task: should update the task")
    public void testUpdateTask() throws Exception {
        Mockito.when(taskService.updateTask(taskUUID, taskRequest)).thenReturn(this.taskResponse);

        mockMvc.perform(put("/api/task/{uuid}", taskUUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(taskRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Delete Task by UUID: should delete task if found")
    public void testDeleteTaskById() throws Exception {
        Mockito.when(taskService.deleteTask(taskUUID)).thenReturn(this.taskResponse);

        mockMvc.perform(delete("/api/task/{uuid}", taskUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Delete Task by UUID: should return not found if task does not exist")
    public void testDeleteTaskByIdNotFound() throws Exception {
        Mockito.when(taskService.deleteTask(taskUUID)).thenThrow(new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(delete("/api/task/{uuid}", taskUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("List Tasks: should return list of tasks")
    public void testListTasks() throws Exception {
        List<TaskResponseDTO> tasks = Arrays.asList(this.taskResponse);

        Mockito.when(taskService.listTasks()).thenReturn(tasks);

        mockMvc.perform(get("/api/task/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task"));
    }
}
