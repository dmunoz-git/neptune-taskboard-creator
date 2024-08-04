package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.task.TaskRequestDTO;
import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.State;
import com.neptune.boards.entity.Task;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.ProjectStateRepository;
import com.neptune.boards.repository.StateRepository;
import com.neptune.boards.repository.TaskRepository;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ProjectStateRepository projectStateRepository;

    @InjectMocks
    private TaskService service;

    private Task task;
    private Project project;
    private State state;
    private ProjectState projectState;
    private UUID taskUUID;
    private UUID projectUUID;
    private UUID stateUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        taskUUID = UUID.randomUUID();
        projectUUID = UUID.randomUUID();
        stateUUID = UUID.randomUUID();
        project = Project.builder().UUID(projectUUID).name("Test Project").build();
        state = State.builder().UUID(stateUUID).name("To Do").build();
        projectState = ProjectState.builder().UUID(UUID.randomUUID()).project(project).state(state).build();
        task = Task.builder().UUID(taskUUID).name("Test Task").description("Test Description").project(project).state(projectState).build();
    }

    @Test
    @DisplayName("Unit Test: Get Task: should return task when found")
    void getTaskSuccessTest() throws NeptuneBoardsException {
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(task));

        TaskResponseDTO response = service.getTask(taskUUID);

        assertNotNull(response);
        assertEquals("Test Task", response.getName());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Get Task: should throw exception if task not found")
    void getTaskNotFoundTest() {
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.getTask(taskUUID)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Create Task: should create task successfully")
    void createTaskSuccessTest() throws NeptuneBoardsException {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "New Description", projectUUID, stateUUID);
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));
        when(stateRepository.findByUUID(stateUUID)).thenReturn(Optional.of(state));
        when(projectStateRepository.findByProjectAndState(project, state)).thenReturn(Optional.of(projectState));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO response = service.createTask(taskUUID, request);

        assertNotNull(response);
        assertEquals("Test Task", response.getName());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
        verify(stateRepository, times(1)).findByUUID(stateUUID);
        verify(projectStateRepository, times(1)).findByProjectAndState(project, state);
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    @DisplayName("Unit Test: Create Task: should throw exception if project not found")
    void createTaskProjectNotFoundTest() {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "New Description", projectUUID, stateUUID);
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.createTask(taskUUID, request)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Create Task: should throw exception if state not found")
    void createTaskStateNotFoundTest() {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "New Description", projectUUID, stateUUID);
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));
        when(stateRepository.findByUUID(stateUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.createTask(taskUUID, request)
        );

        assertEquals("State not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
        verify(stateRepository, times(1)).findByUUID(stateUUID);
    }

    @Test
    @DisplayName("Unit Test: Create Task: should throw exception if project state not found")
    void createTaskProjectStateNotFoundTest() {
        TaskRequestDTO request = new TaskRequestDTO("New Task", "New Description", projectUUID, stateUUID);
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));
        when(stateRepository.findByUUID(stateUUID)).thenReturn(Optional.of(state));
        when(projectStateRepository.findByProjectAndState(project, state)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.createTask(taskUUID, request)
        );

        assertEquals("ProjectState not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
        verify(stateRepository, times(1)).findByUUID(stateUUID);
        verify(projectStateRepository, times(1)).findByProjectAndState(project, state);
    }

    @Test
    @DisplayName("Unit Test: Update Task: should update task when found")
    @Disabled
    void updateTaskSuccessTest() throws NeptuneBoardsException {
        TaskRequestDTO request = new TaskRequestDTO("Updated Task", "Updated Description", projectUUID, stateUUID);
        Task updatedTask = task.updateFromDto(request);
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);

        TaskResponseDTO response = service.updateTask(taskUUID, request);

        assertNotNull(response);
        assertEquals(taskUUID, response.getBoard());
        assertEquals("Updated Task", response.getName());
    }

    @Test
    @DisplayName("Unit Test: Update Task: should throw exception if task not found")
    void updateTaskNotFoundTest() {
        TaskRequestDTO request = new TaskRequestDTO("Updated Task", "Updated Description", projectUUID, stateUUID);
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.updateTask(taskUUID, request)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: Delete Task: should delete task when found")
    void deleteTaskSuccessTest() throws NeptuneBoardsException {
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.of(task));

        TaskResponseDTO response = service.deleteTask(taskUUID);

        assertNotNull(response);
        verify(taskRepository, times(1)).findByUUID(taskUUID);
        verify(taskRepository, times(1)).delete(task);
    }

    @Test
    @DisplayName("Unit Test: Delete Task: should throw exception if task not found")
    void deleteTaskNotFoundTest() {
        when(taskRepository.findByUUID(taskUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.deleteTask(taskUUID)
        );

        assertEquals("Task not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(TaskService.class, exception.getOriginClass());
        verify(taskRepository, times(1)).findByUUID(taskUUID);
    }

    @Test
    @DisplayName("Unit Test: List Tasks: should return list of tasks")
    void listTasksSuccessTest() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<TaskResponseDTO> tasks = service.listTasks();

        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(1, tasks.size());
        verify(taskRepository, times(1)).findAll();
    }
}
