package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Board;
import com.neptune.boards.repository.IBoardRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BoardRepositoryTest {

    @Autowired
    IBoardRepository repository;

    @Autowired
    EntityManager entityManager;

    private Board board;

    @BeforeEach
    public void setUp() {
        this.board = Board.builder()
                .name("Test board")
                .description("Test board description")
                .build();
    }

    @Test
    @DisplayName("Create: should create dashboard in db")
    public void saveDashboardTest() {
        // Save board
        Board savedBoard =  repository.save(board);

        // Test if have been saved and is equals
        Assertions.assertNotNull(savedBoard);
        Assertions.assertEquals("Test board", savedBoard.getName());
    }

    @Test
    @DisplayName("Find: should find dashboard by id")
    public void findDashboardByIdTest() {
        Optional<Board> foundDashboard = repository.findById(board.getId());

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals(board.getId(), foundDashboard.get().getId());
    }

    @Test
    @DisplayName("Exception: should not be able to create a dashboard with the same name")
    public void saveDuplicateDashboardNameTest() {
        Board duplicateDashboard = Board.builder()
                .name("Test board")
                .description("Test board description")
                .build();

        DataIntegrityViolationException exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(duplicateDashboard);
            entityManager.flush();
        });

        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Find: should find dashboard by name")
    public void findDashboardByNameTest() {
        Optional<Board> foundDashboard = repository.findByName("Test Dashboard");

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals("Test Dashboard", foundDashboard.get().getName());
    }

    @Test
    @DisplayName("Update: should update dashboard")
    public void updateDashboardTest() {
        board.setName("Updated Dashboard");

        Board updatedDashboard = repository.save(board);

        Assertions.assertNotNull(updatedDashboard);
        Assertions.assertEquals("Updated Dashboard", updatedDashboard.getName());
    }

    @Test
    @DisplayName("Delete: should delete dashboard by id")
    public void deleteDashboardByIdTest() {
        repository.deleteById(board.getId());

        Optional<Board> foundDashboard = repository.findById(board.getId());

        Assertions.assertFalse(foundDashboard.isPresent());
    }
}
