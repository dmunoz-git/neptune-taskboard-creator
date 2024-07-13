package com.neptune.boards.tests.unit.service;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.BoardRepository;
import com.neptune.boards.service.BoardService;
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

public class BoardServiceTest {

    @Mock
    BoardRepository repository;

    @InjectMocks
    private BoardService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Create Dashboard: should create and return a new board")
    void createBoardTest() {
        Board board = Board.builder()
                .id(1L)
                .UUID(UUID.randomUUID())
                .name("Test Board")
                .description("Test board description")
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .build();

        // Configure to mock repository and create the board
        when(repository.save(any(Board.class))).thenReturn(board);
        Board savedBoard = service.createBoard(board);

        // Test if the board exists
        assertNotNull(savedBoard);

        // Check if the data were saved correctly
        assertEquals(board.getUUID(), savedBoard.getUUID());
        assertEquals(board.getName(), savedBoard.getName());
        assertEquals(board.getDescription(), savedBoard.getDescription());

        // Check if fields createdAt and updatedAt were save correctly
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, savedBoard.getCreatedAt());
        assertEquals(currentDate, savedBoard.getUpdatedAt());

        // Check if the db sets a correct id
        assertNotNull(savedBoard.getId());
        assertInstanceOf(Long.class, savedBoard.getId());

        // Check invocations of repository
        verify(repository, times(1)).save(any(Board.class));
    }

    @Test
    @DisplayName("Get Dashboard by UUID: should return board")
    void getBoardByUUIDTest() throws NeptuneBoardsException {
        Board board = Board.builder().UUID(UUID.randomUUID()).name("Test Dashboard").build();
        when(repository.findByUUID(board.getUUID())).thenReturn(Optional.of(board));

        Board foundBoard = service.getBoard(board.getUUID());

        assertNotNull(foundBoard);
        assertEquals("Test Dashboard", foundBoard.getName());
    }

    @Test
    @DisplayName("Exception Board: should return 404 not found board")
    void getNotFoundBoardTest() {
        NeptuneBoardsException exception = assertThrows(NeptuneBoardsException.class, () -> {
            service.getBoard(UUID.randomUUID());
        });
        assertEquals("Board not found", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    }

    @Test
    @DisplayName("Get All Dashboards: should return list of dashboards")
    void getAllBoardsTest() {
        // Create boards
        Board board1 = Board.builder().name("Dashboard 1").build();
        Board board2 = Board.builder().name("Dashboard 2").build();

        // Mock repo
        when(repository.findAll()).thenReturn(Arrays.asList(board1, board2));

        // Get boards
        List<Board> boards = service.getAllBoards();
        assertNotNull(boards);
        assertEquals(2, boards.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("Update Board: should update a board if some of the updatable fields are null")
    void updateBoardTest() throws NeptuneBoardsException {
        // Create the updated board data and the actual board
        BoardRequestDTO boardRequest = BoardRequestDTO.builder().name("Updated name").build();
        UUID boardUUID = UUID.randomUUID();
        Board board = Board.builder().UUID(boardUUID).name("Old name").description("This is a description").build();

        // Mock repository methods
        Mockito.when(repository.findByUUID(boardUUID)).thenReturn(Optional.of(board));
        Mockito.when(repository.save(any(Board.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Call the service method
        Board updatedBoard = service.updateBoard(boardUUID, boardRequest);

        // Verify the results
        assertEquals(boardRequest.getName(), updatedBoard.getName());
        assertNotNull(updatedBoard.getDescription());
        assertEquals(board.getDescription(), updatedBoard.getDescription());
        assertNotEquals(updatedBoard.getUpdatedAt(), updatedBoard.getCreatedAt());
    }
}
