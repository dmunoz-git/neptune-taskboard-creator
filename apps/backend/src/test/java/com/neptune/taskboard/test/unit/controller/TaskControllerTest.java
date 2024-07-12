package com.neptune.taskboard.test.unit.controller;

import com.neptune.taskboard.controller.TaskController;
import com.neptune.taskboard.entity.Board;
import com.neptune.taskboard.entity.Task;
import com.neptune.taskboard.exception.BoardmasterException;
import com.neptune.taskboard.service.TaskService;
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

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    private Task task;
    private Board dashboard;

    @BeforeEach
    void setUp() {
        this.task = new Task();
        this.task.setName("Test Task");

        this.dashboard = new Board("Test Dashboard");
        this.dashboard.setId(1L);
    }

    @Test
    @DisplayName("Get Task by ID: should return task if found")
    public void testGetTaskById() throws Exception {
        Mockito.when(taskService.getTask(1L)).thenReturn(this.task);

        mockMvc.perform(get("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Get Task by ID: should return not found if task does not exist")
    public void testGetTaskByIdNotFound() throws Exception {
        Mockito.when(taskService.getTask(1L)).thenThrow(new BoardmasterException("Task not found"));

        mockMvc.perform(get("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Create Task: should create and return a new task")
    public void testCreateTask() throws Exception {
        Mockito.when(taskService.create(Mockito.anyLong(), Mockito.any(Task.class))).thenReturn(this.task);

        mockMvc.perform(post("/task")
                        .param("dashboardId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Task\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Update Task: should update the task")
    public void testUpdateTask() throws Exception {
        Mockito.when(taskService.update(1L, this.task)).thenReturn(this.task);

        mockMvc.perform(put("/task/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Task\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Task"));
    }

    @Test
    @DisplayName("Delete Task by ID: should delete task if found")
    public void testDeleteTaskById() throws Exception {
        Mockito.when(taskService.delete(1L)).thenReturn(this.task);

        mockMvc.perform(delete("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    @DisplayName("Delete Task by ID: should return not found if task does not exist")
    public void testDeleteTaskByIdNotFound() throws Exception {
        Mockito.when(taskService.delete(1L)).thenThrow(new BoardmasterException("Task not found"));

        mockMvc.perform(delete("/task/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("List Tasks: should return list of tasks")
    public void testListTasks() throws Exception {
        List<Task> tasks = Arrays.asList(this.task);

        Mockito.when(taskService.listTasks()).thenReturn(tasks);

        mockMvc.perform(get("/task")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Task"));
    }
}
