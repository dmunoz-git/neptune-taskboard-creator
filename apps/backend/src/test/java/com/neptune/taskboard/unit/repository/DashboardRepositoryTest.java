package com.neptune.taskboard.unit.repository;

import com.neptune.taskboard.unit.entity.Dashboard;
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
public class DashboardRepositoryTest {

    @Autowired
    IDashboardRepository repository;

    @Autowired
    EntityManager entityManager;

    private Dashboard dashboard;

    @BeforeEach
    public void setUp() {
        dashboard = new Dashboard("Test Dashboard");
        repository.save(dashboard);
    }

    @Test
    @DisplayName("Create: should create dashboard in db")
    public void saveDashboardTest() {
        Dashboard newDashboard = new Dashboard("New Test Dashboard");

        Dashboard savedDashboard = repository.save(newDashboard);

        Assertions.assertNotNull(savedDashboard);
        Assertions.assertEquals("New Test Dashboard", savedDashboard.getName());
    }

    @Test
    @DisplayName("Find: should find dashboard by id")
    public void findDashboardByIdTest() {
        Optional<Dashboard> foundDashboard = repository.findById(dashboard.getId());

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals(dashboard.getId(), foundDashboard.get().getId());
    }

    @Test
    @DisplayName("Exception: should not be able to create a dashboard with the same name")
    public void saveDuplicateDashboardNameTest() {
        Dashboard duplicateDashboard = new Dashboard("Test Dashboard");

        DataIntegrityViolationException exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> {
            repository.saveAndFlush(duplicateDashboard);
            entityManager.flush();
        });

        Assertions.assertNotNull(exception.getMessage());
    }

    @Test
    @DisplayName("Find: should find dashboard by name")
    public void findDashboardByNameTest() {
        Optional<Dashboard> foundDashboard = repository.findByName("Test Dashboard");

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals("Test Dashboard", foundDashboard.get().getName());
    }

    @Test
    @DisplayName("Update: should update dashboard")
    public void updateDashboardTest() {
        dashboard.setName("Updated Dashboard");

        Dashboard updatedDashboard = repository.save(dashboard);

        Assertions.assertNotNull(updatedDashboard);
        Assertions.assertEquals("Updated Dashboard", updatedDashboard.getName());
    }

    @Test
    @DisplayName("Delete: should delete dashboard by id")
    public void deleteDashboardByIdTest() {
        repository.deleteById(dashboard.getId());

        Optional<Dashboard> foundDashboard = repository.findById(dashboard.getId());

        Assertions.assertFalse(foundDashboard.isPresent());
    }
}
