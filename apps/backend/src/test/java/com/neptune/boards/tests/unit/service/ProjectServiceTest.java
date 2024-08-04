package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.dto.projectState.TaskStateDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.repository.ProjectStateRepository;
import com.neptune.boards.repository.StateRepository;
import com.neptune.boards.service.ProjectService;
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
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private StateRepository stateRepository;

    @Mock
    private ProjectStateRepository projectStateRepository;

    @InjectMocks
    private ProjectService service;

    private Project project;
    private UUID projectUUID;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        projectUUID = UUID.randomUUID();
        project = Project.builder().UUID(projectUUID).name("Test Project").description("Test Description").build();
    }

    @Test
    @DisplayName("Unit Test: Create Board: should create board with default states successfully")
    @Disabled
    void createBoardSuccessTest() {
        List<TaskStateDTO> taskStates = List.of(
                new TaskStateDTO(UUID.randomUUID(), "To Do", "Definition", true),
                new TaskStateDTO(UUID.randomUUID(), "In Progress", "Definition", true),
                new TaskStateDTO(UUID.randomUUID(), "Done", "Definition", true)
        );

        ProjectRequestDTO request = new ProjectRequestDTO("New Project", "New Description", taskStates);

        when(stateRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(projectRepository.save(any(Project.class))).thenReturn(project);

        ProjectResponseDTO response = service.createBoard(projectUUID, request);

        assertNotNull(response);
        assertEquals(projectUUID, response.getUuid());
        assertEquals("Test Project", response.getName());
        verify(projectRepository, times(1)).save(any(Project.class));
        verify(stateRepository, times(3)).findByName(anyString());
        verify(stateRepository, times(3)).save(any(State.class));
        verify(projectStateRepository, times(3)).save(any(ProjectState.class));
    }

    @Test
    @DisplayName("Unit Test: Get Board: should return board when found")
    void getBoardSuccessTest() throws NeptuneBoardsException {
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));

        ProjectResponseDTO response = service.getBoard(projectUUID);

        assertNotNull(response);
        assertEquals(projectUUID, response.getUuid());
        assertEquals("Test Project", response.getName());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Get Board: should throw exception if board not found")
    void getBoardNotFoundTest() {
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.getBoard(projectUUID)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Get All Boards: should return list of boards")
    void getAllBoardsSuccessTest() {
        when(projectRepository.findAll()).thenReturn(List.of(project));

        List<ProjectResponseDTO> projects = service.getAllBoards();

        assertNotNull(projects);
        assertFalse(projects.isEmpty());
        assertEquals(1, projects.size());
        verify(projectRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Unit Test: Delete Board: should delete board when found")
    void deleteBoardSuccessTest() throws NeptuneBoardsException {
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));

        ProjectResponseDTO response = service.deleteBoard(projectUUID);

        assertNotNull(response);
        assertEquals(projectUUID, response.getUuid());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
        verify(projectRepository, times(1)).delete(project);
    }

    @Test
    @DisplayName("Unit Test: Delete Board: should throw exception if board not found")
    void deleteBoardNotFoundTest() {
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.deleteBoard(projectUUID)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
    }

    @Test
    @DisplayName("Unit Test: Update Board: should update board when found")
    void updateBoardSuccessTest() throws NeptuneBoardsException {
        ProjectRequestDTO request = new ProjectRequestDTO("Updated Project", "Updated Description", new ArrayList<>());
        Project updatedProject = project.updateFromDto(request);
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.of(project));
        when(projectRepository.save(any(Project.class))).thenReturn(updatedProject);

        ProjectResponseDTO response = service.updateBoard(projectUUID, request);

        assertNotNull(response);
        assertEquals(projectUUID, response.getUuid());
        assertEquals("Updated Project", response.getName());
    }

    @Test
    @DisplayName("Unit Test: Update Board: should throw exception if board not found")
    void updateBoardNotFoundTest() {
        ProjectRequestDTO request = new ProjectRequestDTO("Updated Project", "Updated Description", new ArrayList<>());
        when(projectRepository.findByUUID(projectUUID)).thenReturn(Optional.empty());

        NeptuneBoardsException exception = assertThrows(
                NeptuneBoardsException.class,
                () -> service.updateBoard(projectUUID, request)
        );

        assertEquals("Project not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
        assertEquals(ProjectService.class, exception.getOriginClass());
        verify(projectRepository, times(1)).findByUUID(projectUUID);
    }
}
