package com.neptune.boards.tests.unit.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neptune.boards.controller.BoardController;
import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.dto.BoardResponseDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.BoardService;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private BoardService service;

    private Board boardTest;
    private final UUID uuid = UUID.randomUUID();
    private BoardRequestDTO requestBoardTest;
    private BoardResponseDTO responseDTOTest;

    @BeforeEach
    void setUp() {
        this.boardTest = Board.builder()
                .id(1L)
                .UUID(this.uuid)
                .name("Test board")
                .description("Test board description")
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        this.requestBoardTest = BoardRequestDTO.builder()
                .name(this.boardTest.getName())
                .description(this.boardTest.getDescription())
                .build();

        this.responseDTOTest = BoardResponseDTO.builder()
                .name(this.boardTest.getName())
                .description(this.boardTest.getDescription())
                .createdAt(this.boardTest.getCreatedAt())
                .updatedAt(this.boardTest.getUpdatedAt())
                .build();
    }

    @Test
    @DisplayName("Create a Board: should return the created board")
    public void createBoardControllerTest() throws Exception {
        String boardRequestJson = objectMapper.writeValueAsString(requestBoardTest);

        Mockito.when(service.createBoard(uuid, requestBoardTest)).thenReturn(this.responseDTOTest);

        mockMvc.perform(post("/api/board/" + this.uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(boardRequestJson))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Exception Board not found: should return not found if board does not exist")
    public void notFoundGetBoardControllerTest() throws Exception {
        UUID uuid = UUID.randomUUID();
        Mockito.when(service.getBoard(uuid)).thenThrow(new NeptuneBoardsException("board not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(get("/api/board/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Get Board by UUID: should return board")
    public void getBoardControllerTest() throws Exception {
        Mockito.when(service.getBoard(uuid)).thenReturn(this.responseDTOTest);

        mockMvc.perform(get("/api/board/" + boardTest.getUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Get All Boards: should return list of boards")
    public void testGetAllboards() throws Exception {
        List<BoardResponseDTO> boards = Arrays.asList(BoardResponseDTO.builder().UUID(UUID.randomUUID()).name("Board1").build(), BoardResponseDTO.builder().UUID(UUID.randomUUID()).name("Board2").build());
        Mockito.when(service.getAllBoards()).thenReturn(boards);

        mockMvc.perform(get("/api/board/list")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Board1"))
                .andExpect(jsonPath("$[1].name").value("Board2"));
    }

    @Test
    @DisplayName("Delete Boards by ID: should delete board if found")
    public void testDeleteboardById() throws Exception {
        Mockito.when(service.deleteBoard(uuid)).thenReturn(this.responseDTOTest);

        mockMvc.perform(delete("/api/board/" + this.boardTest.getUUID())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(this.boardTest.getName()));
    }

    @Test
    @DisplayName("Delete Boards by UUID: should return not found if board does not exist")
    public void testDeleteboardByIdNotFound() throws Exception {
        Mockito.when(service.deleteBoard(uuid)).thenThrow(new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass()));

        mockMvc.perform(delete("/api/board/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Update Board: should return the updated board")
    @Disabled
    public void testUpdateBoard() throws Exception {
        UUID uuid = boardTest.getUUID();
        BoardResponseDTO updatedBoard = BoardResponseDTO.builder()
                .UUID(uuid)
                .name("Updated Board")
                .description(boardTest.getDescription())
                .createdAt(boardTest.getCreatedAt())
                .updatedAt(LocalDate.now())
                .build();

        BoardRequestDTO updateRequest = BoardRequestDTO.builder().name("Updated Board").build();

        // Mock the service to return the updated board when the updateBoard method is called
        Mockito.when(service.updateBoard(uuid, updateRequest)).thenReturn(updatedBoard);

        // Perform the PUT request and verify the response
        mockMvc.perform(put("/api/board/" + uuid)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(updatedBoard.getName()))
                .andExpect(jsonPath("$.description").value(updatedBoard.getDescription()))
                .andExpect(jsonPath("$.UUID").value(updatedBoard.getUUID().toString()));
    }
}
