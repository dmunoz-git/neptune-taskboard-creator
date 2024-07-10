package com.neptune.taskboard.test.unit.service;

import com.neptune.taskboard.unit.entity.Dashboard;
import com.neptune.taskboard.unit.entity.Task;
import com.neptune.taskboard.unit.exception.BoardmasterException;
import com.neptune.taskboard.unit.repository.IDashboardRepository;
import com.neptune.taskboard.unit.repository.ITaskRepository;
import com.neptune.taskboard.unit.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    ITaskRepository taskRepository;

    @Mock
    IDashboardRepository dashboardRepository;

    @InjectMocks
    private TaskService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Get Task by ID: should return task if found")
    void getTaskByIdTest() throws BoardmasterException {
        Task task = new Task();
        task.setName("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task foundTask = service.getTask(1L);

        assertNotNull(foundTask);
        assertEquals("Test Task", foundTask.getName());
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Get Task by ID: should throw exception if not found")
    void getTaskByIdNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.getTask(1L));
        verify(taskRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Create Task: should create and return a new task for a given dashboard")
    void createTaskTest() throws BoardmasterException {
        Task task = new Task();
        task.setName("New Task");
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        Dashboard dashboard = new Dashboard("Test Dashboard");
        dashboard.setId(1L);
        when(dashboardRepository.findById(1L)).thenReturn(Optional.of(dashboard));

        Task createdTask = service.create(1L, task);

        assertNotNull(createdTask);
        assertEquals("New Task", createdTask.getName());
        verify(taskRepository, times(1)).save(any(Task.class));
        verify(dashboardRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Create Task: should throw exception if dashboard not found")
    void createTaskDashboardNotFoundTest() {
        Task task = new Task();
        task.setName("New Task");
        when(dashboardRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.create(1L, task));
        verify(taskRepository, times(0)).save(any(Task.class));
        verify(dashboardRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Update Task: should update and return the task")
    void updateTaskTest() throws BoardmasterException {
        Task existingTask = new Task();
        existingTask.setId(1L);
        existingTask.setName("Existing Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(existingTask));

        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");

        Task savedTask = new Task();
        savedTask.setName("Updated Task");
        savedTask.setDescription("Updated Description");
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = service.update(1L, updatedTask);

        assertNotNull(result);
        assertEquals("Updated Task", result.getName());
        assertEquals("Updated Description", result.getDescription());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).save(existingTask);
    }

    @Test
    @DisplayName("Update Task: should throw exception if task not found")
    void updateTaskNotFoundTest() {
        Task updatedTask = new Task();
        updatedTask.setName("Updated Task");
        updatedTask.setDescription("Updated Description");
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.update(1L, updatedTask));
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(0)).save(any(Task.class));
    }

    @Test
    @DisplayName("Delete Task: should delete the task if found")
    void deleteTaskTest() throws BoardmasterException {
        Task task = new Task();
        task.setName("Test Task");
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task deletedTask = service.delete(1L);

        assertNotNull(deletedTask);
        assertEquals("Test Task", deletedTask.getName());
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Delete Task: should throw exception if task not found")
    void deleteTaskNotFoundTest() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BoardmasterException.class, () -> service.delete(1L));
        verify(taskRepository, times(1)).findById(1L);
        verify(taskRepository, times(0)).delete(any(Task.class));
    }

    @Test
    @DisplayName("List Tasks: should return all tasks")
    void listTasksTest() {
        Task task1 = new Task();
        task1.setName("Task 1");

        Task task2 = new Task();
        task2.setName("Task 2");

        when(taskRepository.findAll()).thenReturn(Arrays.asList(task1, task2));

        List<Task> tasks = service.listTasks();

        assertNotNull(tasks);
        assertEquals(2, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }
}
