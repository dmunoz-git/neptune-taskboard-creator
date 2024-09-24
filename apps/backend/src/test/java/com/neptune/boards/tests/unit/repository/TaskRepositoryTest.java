package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Task;
import com.neptune.boards.repository.TaskRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskRepositoryTest {

    private static final String TASK_NAME = "Test Task";
    private static final String TASK_DESCRIPTION = "Test Description";

    private final UUID savedTaskUUID = UUID.randomUUID();;

    @Autowired
    TaskRepository repository;

    @BeforeAll
    public void setUp() {
        Task task = createTask(savedTaskUUID, TASK_NAME, TASK_DESCRIPTION);
        repository.save(task);
    }

    @Test
    @DisplayName("Unit Test: Find Task: should find task by UUID")
    public void testFindTaskByUUID() {
        Optional<Task> foundTask = repository.findByUUID(this.savedTaskUUID);
        Assertions.assertTrue(foundTask.isPresent());
    }

    private Task createTask(UUID uuid, String name, String description) {
        return Task.builder()
                .UUID(uuid)
                .name(name)
                .description(description)
                .build();
    }
}
