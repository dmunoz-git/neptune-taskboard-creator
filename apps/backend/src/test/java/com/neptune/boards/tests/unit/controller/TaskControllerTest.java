package com.neptune.boards.tests.unit.controller;

import com.neptune.boards.controller.TaskController;
import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.dto.task.TaskRequestDTO;
import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskControllerTest {

    @Mock
    private TaskService service;

    @InjectMocks
    private TaskController controller;

    private TaskRequestDTO taskRequest;
    private TaskResponseDTO taskResponse;
    private UUID taskUUID;
    private UUID boardUUID;
    private UUID stateUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskUUID = UUID.randomUUID();
        boardUUID = UUID.randomUUID();
        stateUUID = UUID.randomUUID();
        taskRequest = new TaskRequestDTO("Test Task", "Test Description", boardUUID, stateUUID);
        taskResponse = new TaskResponseDTO("Test Task", "Test Description", LocalDate.now(), LocalDate.now(), new StateResponseDTO(taskUUID, "To Do", null), boardUUID);
    }

    @Test
    @DisplayName("Unit Test: Get Task by UUID: should return task when found")
    void getTaskByIdSuccessTest() throws NeptuneBoardsException {
        when(service.getTask(any(UUID.class))).thenReturn(taskResponse);

        ResponseEntity<TaskResponseDTO> response = controller.getTaskById(taskUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
        verify(service, times(1)).getTask(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Get Task by UUID: should throw exception if task not found")
    void getTaskByIdNotFoundTest() throws NeptuneBoardsException {
        when(service.getTask(any(UUID.class))).thenThrow(new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, TaskController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.getTaskById(taskUUID)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskController.class, exception.getOriginClass());
        verify(service, times(1)).getTask(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Create Task: should create task successfully")
    void createTaskSuccessTest() throws NeptuneBoardsException {
        when(service.createTask(any(UUID.class), any(TaskRequestDTO.class))).thenReturn(taskResponse);

        ResponseEntity<TaskResponseDTO> response = controller.createTask(taskUUID, taskRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
        verify(service, times(1)).createTask(taskUUID, taskRequest);
    }

    @Test
    @DisplayName("Unit Test: Delete Task: should delete task successfully")
    void deleteTaskSuccessTest() throws NeptuneBoardsException {
        when(service.deleteTask(any(UUID.class))).thenReturn(taskResponse);

        ResponseEntity<TaskResponseDTO> response = controller.deleteTask(taskUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
        verify(service, times(1)).deleteTask(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Delete Task: should throw exception if task not found")
    void deleteTaskNotFoundTest() throws NeptuneBoardsException {
        when(service.deleteTask(any(UUID.class))).thenThrow(new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, TaskController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.deleteTask(taskUUID)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskController.class, exception.getOriginClass());
        verify(service, times(1)).deleteTask(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Update Task: should update task successfully")
    void updateTaskSuccessTest() throws NeptuneBoardsException {
        when(service.updateTask(any(UUID.class), any(TaskRequestDTO.class))).thenReturn(taskResponse);

        ResponseEntity<TaskResponseDTO> response = controller.updateTask(taskUUID, taskRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(taskResponse, response.getBody());
        verify(service, times(1)).updateTask(taskUUID, taskRequest);
    }

    @Test
    @DisplayName("Unit Test: Update Task: should throw exception if task not found")
    void updateTaskNotFoundTest() throws NeptuneBoardsException {
        when(service.updateTask(any(UUID.class), any(TaskRequestDTO.class))).thenThrow(new NeptuneBoardsException("Task not found", HttpStatus.NOT_FOUND, TaskController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.updateTask(taskUUID, taskRequest)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskController.class, exception.getOriginClass());
        verify(service, times(1)).updateTask(taskUUID, taskRequest);
    }

    @Test
    @DisplayName("Unit Test: List Tasks: should return list of tasks")
    void listTasksSuccessTest() {
        when(service.listTasks()).thenReturn(List.of(taskResponse));

        ResponseEntity<List<TaskResponseDTO>> response = controller.listTasks();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).listTasks();
    }
}
