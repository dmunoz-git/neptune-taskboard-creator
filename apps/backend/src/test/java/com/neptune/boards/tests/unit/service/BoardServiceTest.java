package com.neptune.boards.tests.unit.service;

import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.BoardRepository;
import com.neptune.boards.service.BoardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    @DisplayName("Create Dashboard: should create and return a new dashboard")
    void createDashboardTest() {
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
    @DisplayName("Get Dashboard by UUID: should return dashboard if found")
    void getDashboardByIdTest() throws NeptuneBoardsException {
        Board board = Board.builder().UUID(UUID.randomUUID()).name("Test Dashboard").build();
        when(repository.findByUUID(board.getUUID())).thenReturn(Optional.of(board));

        Board foundBoard = service.getBoard(board.getUUID());

        assertNotNull(foundBoard);
        assertEquals("Test Dashboard", foundBoard.getName());
    }

    @Test
    @DisplayName("Exception Board: get board not found")
    void testNotFoundBoard() {
        NeptuneBoardsException exception = assertThrows(NeptuneBoardsException.class, () -> {
            service.getBoard(UUID.randomUUID());
        });
        assertEquals("Board not found", exception.getMessage());
    }

    @Test
    @DisplayName("Get All Dashboards: should return list of dashboards")
    void getAllDashboardsTest() {
        Board dashboard1 = Board.builder().name("Dashboard 1").build();
        Board dashboard2 = Board.builder().name("Dashboard 2").build();
        when(repository.findAll()).thenReturn(Arrays.asList(dashboard1, dashboard2));

        List<Board> dashboards = service.getAllBoards();

        assertNotNull(dashboards);
        assertEquals(2, dashboards.size());
        verify(repository, times(1)).findAll();
    }
}
