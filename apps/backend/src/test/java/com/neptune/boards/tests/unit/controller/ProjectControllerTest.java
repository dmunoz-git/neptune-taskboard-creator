package com.neptune.boards.tests.unit.controller;

import com.neptune.boards.controller.ProjectController;
import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.ProjectService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectControllerTest {

    @Mock
    private ProjectService service;

    @InjectMocks
    private ProjectController controller;

    private ProjectRequestDTO projectRequest;
    private ProjectResponseDTO projectResponse;
    private UUID projectUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectUUID = UUID.randomUUID();
        projectRequest = new ProjectRequestDTO("Test Project", "Test Description", List.of());
        projectResponse = new ProjectResponseDTO(projectUUID, "Test Project", "Test Description", LocalDate.now(), LocalDate.now(), List.of());
    }

    @Test
    @DisplayName("Unit Test: Create Project: should create project successfully")
    void createProjectSuccessTest() {
        when(service.createBoard(any(UUID.class), any(ProjectRequestDTO.class))).thenReturn(projectResponse);

        ResponseEntity<ProjectResponseDTO> response = controller.createProject(projectUUID, projectRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(projectResponse, response.getBody());
        verify(service, times(1)).createBoard(projectUUID, projectRequest);
    }

    @Test
    @DisplayName("Unit Test: Get Project: should return project when found")
    void getProjectSuccessTest() throws NeptuneBoardsException {
        when(service.getBoard(any(UUID.class))).thenReturn(projectResponse);

        ResponseEntity<ProjectResponseDTO> response = controller.getProject(projectUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectResponse, response.getBody());
        verify(service, times(1)).getBoard(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Get Project: should throw exception if project not found")
    void getProjectNotFoundTest() throws NeptuneBoardsException {
        when(service.getBoard(any(UUID.class))).thenThrow(new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, ProjectController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.getProject(projectUUID)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectController.class, exception.getOriginClass());
        verify(service, times(1)).getBoard(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Get All Projects: should return list of projects")
    void getAllProjectsSuccessTest() {
        when(service.getAllBoards()).thenReturn(List.of(projectResponse));

        ResponseEntity<List<ProjectResponseDTO>> response = controller.getAllProjects();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(1, response.getBody().size());
        verify(service, times(1)).getAllBoards();
    }

    @Test
    @DisplayName("Unit Test: Delete Project: should delete project successfully")
    void deleteProjectSuccessTest() throws NeptuneBoardsException {
        when(service.deleteBoard(any(UUID.class))).thenReturn(projectResponse);

        ResponseEntity<ProjectResponseDTO> response = controller.deleteProject(projectUUID);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectResponse, response.getBody());
        verify(service, times(1)).deleteBoard(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Delete Project: should throw exception if project not found")
    void deleteProjectNotFoundTest() throws NeptuneBoardsException {
        when(service.deleteBoard(any(UUID.class))).thenThrow(new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, ProjectController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.deleteProject(projectUUID)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectController.class, exception.getOriginClass());
        verify(service, times(1)).deleteBoard(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Update Project: should update project successfully")
    void updateProjectSuccessTest() throws NeptuneBoardsException {
        when(service.updateBoard(any(UUID.class), any(ProjectRequestDTO.class))).thenReturn(projectResponse);

        ResponseEntity<ProjectResponseDTO> response = controller.updateProject(projectUUID, projectRequest);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(projectResponse, response.getBody());
        verify(service, times(1)).updateBoard(projectUUID, projectRequest);
    }

    @Test
    @DisplayName("Unit Test: Update Project: should throw exception if project not found")
    void updateProjectNotFoundTest() throws NeptuneBoardsException {
        when(service.updateBoard(any(UUID.class), any(ProjectRequestDTO.class))).thenThrow(new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, ProjectController.class));

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> controller.updateProject(projectUUID, projectRequest)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectController.class, exception.getOriginClass());
        verify(service, times(1)).updateBoard(projectUUID, projectRequest);
    }
}
