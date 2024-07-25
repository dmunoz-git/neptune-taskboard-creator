package com.neptune.boards.tests.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptune.boards.controller.ProjectController;
import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.ProjectService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProjectService service;

    private Project projectTest;
    private final UUID projectUUID = UUID.randomUUID();
    private ProjectRequestDTO requestBoardTest;
    private ProjectResponseDTO responseDTOTest;

    @BeforeEach
    void setUp() {
        this.projectTest = Project.builder()
                .UUID(this.projectUUID)
                .name("Test project")
                .description("Test project description")
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        this.requestBoardTest = ProjectRequestDTO.builder()
                .name(this.projectTest.getName())
                .description(this.projectTest.getDescription())
                .build();

        this.responseDTOTest = ProjectResponseDTO.builder()
                .name(this.projectTest.getName())
                .description(this.projectTest.getDescription())
                .createdAt(this.projectTest.getCreatedAt())
                .updatedAt(this.projectTest.getUpdatedAt())
                .build();
    }

    @Test
    @DisplayName("Unit Test: Create a Project: should return the created project")
    public void createBoardControllerTest() throws Exception {
        String boardRequestJson = objectMapper.writeValueAsString(requestBoardTest);

        Mockito.when(service.createBoard(this.projectUUID, requestBoardTest)).thenReturn(this.responseDTOTest);

        mockMvc.perform(post("/api/project/" + this.projectTest)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardRequestJson))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    @DisplayName("Exception Project not found: should return not found if project does not exist")
    public void notFoundGetBoardControllerTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(service.getBoard(uuid)).thenThrow(new NeptuneBoardsException("project not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(get("/api/project/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Project by UUID: should return project")
    public void getBoardControllerTest() throws Exception {
        Mockito.when(service.getBoard(this.projectUUID)).thenReturn(this.responseDTOTest);

        mockMvc.perform(get("/api/project/" + projectTest.getUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get All Boards: should return list of boards")
    public void testGetAllboards() throws Exception {
        List<ProjectResponseDTO> boards = Arrays.asList(ProjectResponseDTO.builder().uuid(UUID.randomUUID()).name("Board1").build(), ProjectResponseDTO.builder().uuid(UUID.randomUUID()).name("Board2").build());
        Mockito.when(service.getAllBoards()).thenReturn(boards);

        mockMvc.perform(get("/api/project/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Board1"))
                .andExpect(jsonPath("$[1].name").value("Board2"));
    }

    @Test
    @DisplayName("Delete Boards by ID: should delete project if found")
    public void testDeleteboardById() throws Exception {
        Mockito.when(service.deleteBoard(this.projectUUID)).thenReturn(this.responseDTOTest);

        mockMvc.perform(delete("/api/project/" + this.projectTest.getUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(this.projectTest.getName()));
    }

    @Test
    @DisplayName("Delete Boards by UUID: should return not found if project does not exist")
    public void testDeleteboardByIdNotFound() throws Exception {
        Mockito.when(service.deleteBoard(this.projectUUID)).thenThrow(new NeptuneBoardsException("Project not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(delete("/api/project/" + this.projectUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update Project: should return the updated project")
    @Disabled
    public void testUpdateBoard() throws Exception {
        UUID uuid = projectTest.getUUID();
        ProjectResponseDTO updatedBoard = ProjectResponseDTO.builder()
                .uuid(uuid)
                .name("Updated Project")
                .description(projectTest.getDescription())
                .createdAt(projectTest.getCreatedAt())
                .updatedAt(LocalDate.now())
                .build();

        ProjectRequestDTO updateRequest = ProjectRequestDTO.builder().name("Updated Project").build();

        // Mock the service to return the updated project when the updateBoard method is called
        Mockito.when(service.updateBoard(uuid, updateRequest)).thenReturn(updatedBoard);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/project/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedBoard.getName()))
                .andExpect(jsonPath("$.description").value(updatedBoard.getDescription()))
                .andExpect(jsonPath("$.UUID").value(updatedBoard.getUuid().toString()));
    }
}
