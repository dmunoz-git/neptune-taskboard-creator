package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.dto.TaskResponseDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.entity.State;
import com.neptune.boards.entity.Task;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.TaskMapper;
import com.neptune.boards.repository.BoardRepository;
import com.neptune.boards.repository.TaskRepository;
import com.neptune.boards.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    TaskRepository taskRepository;

    @Mock
    BoardRepository boardRepository;

    @InjectMocks
    private TaskService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get Task by UUID: should return task if found")
    void getTaskByUUIDTest() throws NeptuneBoardsException {
        UUID taskUUID = UUID.randomUUID();
        Task task = Task.builder().name("Test Task").UUID(taskUUID).build();
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(task));

        TaskResponseDTO foundTask = service.getTask(taskUUID);

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getName());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Get Task by UUID: should throw exception if not found")
    void getTaskByUUIDNotFoundTest() {
        UUID taskUUID = UUID.randomUUID();
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        assertThrows(NeptuneBoardsException.class, () -> service.getTask(taskUUID));
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Create Task: should create and return a new task for a given board")
    void createTaskTest() throws NeptuneBoardsException {
        UUID boardUUID = UUID.randomUUID();
        Task task = Task.builder().name("New Task").build();
        Board board = Board.builder().name("Test Board").UUID(boardUUID).build();

        TaskRequestDTO requestDTO = TaskRequestDTO.builder()
                .name("New Task")
                .description("New Description")
                .board(boardUUID)
                .build();

        when(boardRepository.findByUUID(boardUUID)).thenReturn(Optional.of(board));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO createdTask = service.createTask(boardUUID, requestDTO);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getName());
        verify(boardRepository, times(1)).findByUUID(boardUUID);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Create Task: should throw exception if board not found")
    void createTaskBoardNotFoundTest() {
        UUID boardUUID = UUID.randomUUID();
        TaskRequestDTO requestDTO = TaskRequestDTO.builder()
                .name("New Task")
                .description("New Description")
                .board(boardUUID)
                .build();

        when(boardRepository.findByUUID(boardUUID)).thenReturn(Optional.empty());

        assertThrows(NeptuneBoardsException.class, () -> service.createTask(boardUUID, requestDTO));
        verify(taskRepository, times(0)).save(any(Task.class));
        verify(boardRepository, times(1)).findByUUID(boardUUID);
    }

    @Test
    @DisplayName("Update Task: should update and return the task")
    void updateTaskTest() throws NeptuneBoardsException {
        UUID taskUUID = UUID.randomUUID();
        Task existingTask = Task.builder().UUID(taskUUID).name("Existing Task").build();

        TaskRequestDTO updateRequest = TaskRequestDTO.builder()
                .name("Updated Task")
                .description("Updated Description")
                .build();

        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(existingTask);

        TaskResponseDTO updatedTask = service.updateTask(taskUUID, updateRequest);

        assertNotNull(updatedTask);
        assertEquals("Updated Task", updatedTask.getName());
        assertEquals("Updated Description", updatedTask.getDescription());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Update Task: should throw exception if task not found")
    void updateTaskNotFoundTest() {
        UUID taskUUID = UUID.randomUUID();
        TaskRequestDTO updateRequest = TaskRequestDTO.builder()
                .name("Updated Task")
                .description("Updated Description")
                .build();

        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        assertThrows(NeptuneBoardsException.class, () -> service.updateTask(taskUUID, updateRequest));
        verify(taskRepository, times(1)).findByUUID(taskUUID);
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    @DisplayName("Delete Task: should delete the task if found")
    void deleteTaskTest() throws NeptuneBoardsException {
        UUID taskUUID = UUID.randomUUID();
        Task task = Task.builder().UUID(taskUUID).name("Test Task").build();

        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(task));

        TaskResponseDTO deletedTask = service.deleteTask(taskUUID);

        assertNotNull(deletedTask);
        assertEquals("Test Task", deletedTask.getName());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Delete Task: should throw exception if task not found")
    void deleteTaskNotFoundTest() {
        UUID taskUUID = UUID.randomUUID();
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        assertThrows(NeptuneBoardsException.class, () -> service.deleteTask(taskUUID));
        verify(taskRepository, times(1)).findByUUID(taskUUID);
        verify(taskRepository, times(0)).delete(any(Task.class));
    }

    @Test
    @DisplayName("List Tasks: should return all tasks")
    void listTasksTest() {
        State state = State.builder().name("To-do").UUID(UUID.randomUUID()).build();
        Task task1 = Task.builder().name("Task 1").state(state).build();
        Task task2 = Task.builder().name("Task 2").state(state).build();

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<TaskResponseDTO> tasks = service.listTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }
}
