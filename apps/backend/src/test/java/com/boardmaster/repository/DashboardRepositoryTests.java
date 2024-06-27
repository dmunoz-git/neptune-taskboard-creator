package com.boardmaster.repository;

import com.boardmaster.entity.Dashboard;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DashboardRepositoryTests {

    @Autowired
    IDashboardRepository repository;

    private Dashboard dashboard;

    @BeforeEach
    public void setUp() {
        dashboard = new Dashboard("Test Dashboard");
        repository.save(dashboard);
    }

    @Test
    @DisplayName("Dashboard Repository Unit Test: should save dashboard in db")
    public void saveDashboardTest() {
        Dashboard newDashboard = new Dashboard("New Test Dashboard");

        Dashboard savedDashboard = repository.save(newDashboard);

        Assertions.assertNotNull(savedDashboard);
        Assertions.assertEquals("New Test Dashboard", savedDashboard.getName());
    }

    @Test
    @DisplayName("Dashboard Repository Unit Test: should find dashboard by id")
    public void findDashboardByIdTest() {
        Optional<Dashboard> foundDashboard = repository.findById(dashboard.getId());

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals(dashboard.getId(), foundDashboard.get().getId());
    }

    @Test
    @DisplayName("Dashboard Repository Unit Test: should find dashboard by name")
    public void findDashboardByNameTest() {
        Optional<Dashboard> foundDashboard = repository.findByName("Test Dashboard");

        Assertions.assertTrue(foundDashboard.isPresent());
        Assertions.assertEquals("Test Dashboard", foundDashboard.get().getName());
    }

    @Test
    @DisplayName("Dashboard Repository Unit Test: should update dashboard")
    public void updateDashboardTest() {
        dashboard.setName("Updated Dashboard");

        Dashboard updatedDashboard = repository.save(dashboard);

        Assertions.assertNotNull(updatedDashboard);
        Assertions.assertEquals("Updated Dashboard", updatedDashboard.getName());
    }

    @Test
    @DisplayName("Dashboard Repository Unit Test: should delete dashboard by id")
    public void deleteDashboardByIdTest() {
        repository.deleteById(dashboard.getId());

        Optional<Dashboard> foundDashboard = repository.findById(dashboard.getId());

        Assertions.assertFalse(foundDashboard.isPresent());
    }
}
