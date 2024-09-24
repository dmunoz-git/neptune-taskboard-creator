package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Project;
import com.neptune.boards.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectRepositoryTest {

    @Autowired
    ProjectRepository repository;

    @Autowired
    EntityManager entityManager;

    private static final String PROJECT_NAME = "Test project";
    private static final String PROJECT_DESCRIPTION = "Test project description";

    private Project project;

    @BeforeEach
    public void setUp() {
        this.project = createProject(UUID.randomUUID(), PROJECT_NAME, PROJECT_DESCRIPTION);
    }

    @Test
    @DisplayName("Unit Test: Find Project: should find project by UUID")
    public void testFindProjectByUUID() {
        // Save the project and check if it exists
        repository.save(this.project);

        Optional<Project> foundProject = repository.findByUUID(this.project.getUUID());

        assertTrue(foundProject.isPresent());
    }

    @Test
    @DisplayName("Delete: should delete project by UUID")
    public void testDeleteProjectByUUID() {
        // Save the project before attempting to delete it
        repository.save(this.project);

        repository.deleteByUUID(project.getUUID());

        Optional<Project> foundProject = repository.findByUUID(project.getUUID());

        assertFalse(foundProject.isPresent());
    }

    private Project createProject(UUID uuid, String name, String description) {
        return Project.builder()
                .UUID(uuid)
                .name(name)
                .description(description)
                .build();
    }
}
