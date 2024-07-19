package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.State;
import com.neptune.boards.entity.Task;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.repository.StateRepository;
import com.neptune.boards.repository.TaskRepository;
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
    TaskRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StateRepository stateRepository;

    private Task task;
    private Project project;
    private State state;

    @BeforeEach
    public void setUp() {
        project =  Project.builder().name("Test project").build();
        projectRepository.save(project);

        state = State.builder().name("Test State").build();
        stateRepository.save(state);

        task = Task.builder()
                .name("Test Task")
                .description("Test Description")
                .project(project)
                .state(state).build();
        repository.save(task);
    }

    @Test
    @DisplayName("Task Repository Unit Test: should save task in db")
    public void saveTaskTest() {
        Task newTask = Task.builder()
                .name("New Test Task")
                .description("New Test Description")
                .project(project)
                .state(state).build();

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
