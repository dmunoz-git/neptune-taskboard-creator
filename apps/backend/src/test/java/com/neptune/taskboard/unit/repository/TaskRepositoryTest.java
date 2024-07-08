package com.neptune.taskboard.unit.repository;

import com.neptune.taskboard.unit.entity.Dashboard;
import com.neptune.taskboard.unit.entity.State;
import com.neptune.taskboard.unit.entity.Task;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TaskRepositoryTest {

    @Autowired
    ITaskRepository repository;

    @Autowired
    IDashboardRepository dashboardRepository;

    @Autowired
    IStateRepository stateRepository;

    private Task task;
    private Dashboard dashboard;
    private State state;

    @BeforeEach
    public void setUp() {
        dashboard = new Dashboard("Test Dashboard");
        dashboardRepository.save(dashboard);

        state = new State();
        state.setName("Test State");
        stateRepository.save(state);

        task = new Task();
        task.setName("Test Task");
        task.setDescription("Test Description");
        task.setDashboard(dashboard);
        task.setState(state);
        repository.save(task);
    }

    @Test
    @DisplayName("Task Repository Unit Test: should save task in db")
    public void saveTaskTest() {
        Task newTask = new Task();
        newTask.setName("New Test Task");
        newTask.setDescription("New Test Description");
        newTask.setDashboard(dashboard);
        newTask.setState(state);

        Task savedTask = repository.save(newTask);

        Assertions.assertNotNull(savedTask);
        Assertions.assertEquals("New Test Task", savedTask.getName());
    }

    @Test
    @DisplayName("Task Repository Unit Test: should find task by id")
    public void findTaskByIdTest() {
        Optional<Task> foundTask = repository.findById(task.getId());

        Assertions.assertTrue(foundTask.isPresent());
        Assertions.assertEquals(task.getId(), foundTask.get().getId());
    }

    @Test
    @DisplayName("Task Repository Unit Test: should find all tasks")
    public void listTasksTest() {
        List<Task> tasks = repository.findAll();

        Assertions.assertFalse(tasks.isEmpty());
        Assertions.assertTrue(tasks.stream().anyMatch(t -> t.getId().equals(task.getId())));
    }

    @Test
    @DisplayName("Task Repository Unit Test: should update task")
    public void updateTaskTest() {
        task.setName("Updated Task");
        task.setDescription("Updated Description");

        Task updatedTask = repository.save(task);

        Assertions.assertNotNull(updatedTask);
        Assertions.assertEquals("Updated Task", updatedTask.getName());
        Assertions.assertEquals("Updated Description", updatedTask.getDescription());
    }

    @Test
    @DisplayName("Task Repository Unit Test: should delete task by id")
    public void deleteTaskByIdTest() {
        repository.deleteById(task.getId());

        Optional<Task> foundTask = repository.findById(task.getId());

        Assertions.assertFalse(foundTask.isPresent());
    }
}
