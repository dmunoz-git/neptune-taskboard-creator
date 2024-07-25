package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    ProjectRepository repository;

    @InjectMocks
    private ProjectService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create project: should create and return a new project")
    void createBoardTest() {
        ProjectRequestDTO requestDTO = ProjectRequestDTO.builder()
                .name("Test project")
                .description("Test project description")
                .build();

        UUID uuid = UUID.randomUUID();

        Project project = Project.builder()
                .id(1L)
                .UUID(UUID.randomUUID())
                .name(requestDTO.getName())
                .description(requestDTO.getDescription())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        // Configure to mock repository and create the project
        when(repository.save(any(Project.class))).thenReturn(project);
        ProjectResponseDTO savedBoard = service.createBoard(uuid, requestDTO);

        // Test if the project exists
        assertNotNull(savedBoard);

        // Check if the data were saved correctly
        assertEquals(project.getUUID(), savedBoard.getUuid());
        assertEquals(project.getName(), savedBoard.getName());
        assertEquals(project.getDescription(), savedBoard.getDescription());

        // Check if fields createdAt and updatedAt were save correctly
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, savedBoard.getCreatedAt());
        assertEquals(currentDate, savedBoard.getUpdatedAt());

        // Check invocations of repository
        verify(repository, times(1)).save(any(Project.class));
    }

    @Test
    @DisplayName("Get project by UUID: should return project")
    void getBoardByUUIDTest() throws NeptuneBoardsException {
        Project project = Project.builder().UUID(UUID.randomUUID()).name("Test project").build();
        when(repository.findByUUID(project.getUUID())).thenReturn(Optional.of(project));

        ProjectResponseDTO foundBoard = service.getBoard(project.getUUID());

        assertNotNull(foundBoard);
        assertEquals("Test project", foundBoard.getName());
    }

    @Test
    @DisplayName("Exception Project: should return 404 not found project")
    void getNotFoundBoardTest() {
        NeptuneBoardsException exception = assertThrows(NeptuneBoardsException.class, () -> {
            service.getBoard(UUID.randomUUID());
        });
        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Get All boards: should return list of boards")
    void getAllBoardsTest() {
        // Create boards
        Project project1 = Project.builder().name("project 1").build();
        Project project2 = Project.builder().name("project 2").build();

        // Mock repo
        when(repository.findAll()).thenReturn(Arrays.asList(project1, project2));

        // Get boards
        List<ProjectResponseDTO> boards = service.getAllBoards();
        assertNotNull(boards);
        assertEquals(2, boards.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update Project: should update a project if some of the updatable fields are null")
    void updateBoardTest() throws NeptuneBoardsException {
        // Create the updated project data and the actual project
        ProjectRequestDTO boardRequest = ProjectRequestDTO.builder().name("Updated name").build();
        UUID boardUUID = UUID.randomUUID();
        Project project = Project.builder().UUID(boardUUID).name("Old name").description("This is a description").build();

        // Mock repository methods
        Mockito.when(repository.findByUUID(boardUUID)).thenReturn(Optional.of(project));
        Mockito.when(repository.save(any(Project.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        ProjectResponseDTO updatedBoard = service.updateBoard(boardUUID, boardRequest);

        // Verify the results
        assertEquals(boardRequest.getName(), updatedBoard.getName());
        assertNotNull(updatedBoard.getDescription());
        assertEquals(project.getDescription(), updatedBoard.getDescription());
        assertNotEquals(updatedBoard.getUpdatedAt(), updatedBoard.getCreatedAt());
    }
}
