package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Board;
import com.neptune.boards.repository.BoardRepository;
import jakarta.persistence.EntityManager;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardRepositoryTest {

    @Autowired
    BoardRepository repository;

    @Autowired
    EntityManager entityManager;

    private Board board;

    @BeforeEach
    public void setUp() {
        this.board = Board.builder()
                .UUID(UUID.randomUUID())
                .name("Test board")
                .description("Test board description")
                .build();
    }

    @Test
    @DisplayName("Create Board: should save a board and autoconfigure the dates")
    public void saveBoardTest() {
        entityManager.persist(this.board);
        entityManager.flush();

        Board savedBoard = repository.findByUUID(board.getUUID()).orElseThrow();

        // Check if the data were saved correctly
        assertEquals(this.board.getUUID(), savedBoard.getUUID());
        assertEquals(this.board.getName(), savedBoard.getName());
        assertEquals(this.board.getDescription(), savedBoard.getDescription());

        // Check if fields createdAt and updatedAt were save correctly
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, savedBoard.getCreatedAt());
        assertEquals(currentDate, savedBoard.getUpdatedAt());

        // Check if the db sets a correct id
        assertNotNull(savedBoard.getId());
        assertInstanceOf(Long.class, savedBoard.getId());
    }

    @Test
    @DisplayName("Exception Board: should not save a board without uuid")
    public void saveBoardWithoutUUIDTest() {
        // Create board without required elements as uuid
        Board newBoard = Board.builder().name("Test board without UUID").build();

        // Test if the validation notNull is working
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(newBoard);
            entityManager.flush(); // Flush to force the database interaction
        });

    }

    @Test
    @DisplayName("Find Board: should find dashboard by UUID")
    public void findDashboardByIdTest() {
        // Save the board and check if exist
        repository.save(this.board);

        Optional<Board> foundDashboard = repository.findByUUID(this.board.getUUID());

        assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals(this.board.getUUID(), foundDashboard.get().getUUID());
    }

    @Test
    @DisplayName("Exception: should not be able to create a dashboard with the same name")
    public void saveDuplicateDashboardNameTest() {
        Board duplicateDashboard = Board.builder()
                .name("Test board")
                .description("Test board description")
                .build();

        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            repository.saveAndFlush(duplicateDashboard);
            entityManager.flush();
        });

        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Update: should update dashboard and update the date")
    public void updateDashboardTest() {
        // Update fields name and description
        this.board.setName("Updated Board name");
        this.board.setDescription("Update Description");

        Board updatedBoard = repository.save(this.board);

        Assertions.assertNotNull(updatedBoard);
        Assertions.assertEquals("Updated Board name", updatedBoard.getName());
        Assertions.assertEquals("Update Description", updatedBoard.getDescription());

        // Check the field updatedAt has been updated
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, updatedBoard.getUpdatedAt());
    }

    @Test
    @DisplayName("Delete: should delete dashboard by uuid")
    public void deleteDashboardByIdTest() {
        repository.deleteByUUID(board.getUUID());

        Optional<Board> foundDashboard = repository.findByUUID(board.getUUID());

        assertFalse(foundDashboard.isPresent());
    }
}
