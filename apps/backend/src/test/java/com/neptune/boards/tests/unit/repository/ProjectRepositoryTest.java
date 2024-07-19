package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Project;
import com.neptune.boards.repository.ProjectRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
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

    private Project project;

    @BeforeEach
    public void setUp() {
        this.project = Project.builder()
                .UUID(UUID.randomUUID())
                .name("Test project")
                .description("Test project description")
                .build();
    }

    @Test
    @DisplayName("Create Project: should save a project and autoconfigure the dates")
    public void saveBoardTest() {
        entityManager.persist(this.project);
        entityManager.flush();

        Project savedProject = repository.findByUUID(project.getUUID()).orElseThrow();

        // Check if the data were saved correctly
        assertEquals(this.project.getUUID(), savedProject.getUUID());
        assertEquals(this.project.getName(), savedProject.getName());
        assertEquals(this.project.getDescription(), savedProject.getDescription());

        // Check if fields createdAt and updatedAt were save correctly
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, savedProject.getCreatedAt());
        assertEquals(currentDate, savedProject.getUpdatedAt());

        // Check if the db sets a correct id
        assertNotNull(savedProject.getId());
        assertInstanceOf(Long.class, savedProject.getId());
    }

    @Test
    @DisplayName("Exception Project: should not save a project without uuid")
    public void saveBoardWithoutUUIDTest() {
        // Create project without required elements as uuid
        Project newProject = Project.builder().name("Test project without UUID").build();

        // Test if the validation notNull is working
        ConstraintViolationException exception = assertThrows(ConstraintViolationException.class, () -> {
            entityManager.persist(newProject);
            entityManager.flush(); // Flush to force the database interaction
        });

    }

    @Test
    @DisplayName("Find Project: should find project by UUID")
    public void findboardByIdTest() {
        // Save the project and check if exist
        repository.save(this.project);

        Optional<Project> foundboard = repository.findByUUID(this.project.getUUID());

        assertTrue(foundboard.isPresent());
        Assertions.assertEquals(this.project.getUUID(), foundboard.get().getUUID());
    }

    @Test
    @DisplayName("Exception: should not be able to create a project with the same name")
    public void saveDuplicateboardNameTest() {
        Project duplicateboard = Project.builder()
                .name("Test project")
                .description("Test project description")
                .build();

        DataIntegrityViolationException exception = assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(duplicateboard);
            entityManager.flush();
        });

        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Update: should update project and update the date")
    public void updateboardTest() {
        // Update fields name and description
        this.project.setName("Updated Project name");
        this.project.setDescription("Update Description");

        Project updatedProject = repository.save(this.project);

        Assertions.assertNotNull(updatedProject);
        Assertions.assertEquals("Updated Project name", updatedProject.getName());
        Assertions.assertEquals("Update Description", updatedProject.getDescription());

        // Check the field updatedAt has been updated
        LocalDate currentDate = LocalDate.now();
        assertEquals(currentDate, updatedProject.getUpdatedAt());
    }

    @Test
    @DisplayName("Delete: should delete project by uuid")
    public void deleteboardByIdTest() {
        repository.deleteByUUID(project.getUUID());

        Optional<Project> foundboard = repository.findByUUID(project.getUUID());

        assertFalse(foundboard.isPresent());
    }
}
