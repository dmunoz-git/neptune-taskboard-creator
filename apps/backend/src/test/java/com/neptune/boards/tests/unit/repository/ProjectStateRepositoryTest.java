package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.ProjectState;
import com.neptune.boards.entity.State;
import com.neptune.boards.repository.ProjectStateRepository;
import com.neptune.boards.repository.ProjectRepository;
import com.neptune.boards.repository.StateRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProjectStateRepositoryTest {
    private static final String PROJECT_NAME = "Test Project";
    private static final String STATE_NAME = "Test State";

    private UUID savedProjectStateUUID;
    private Project mockedProject;
    private State mockedState;

    @Autowired
    ProjectStateRepository repository;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StateRepository stateRepository;

    @BeforeAll
    public void setUp() {
        savedProjectStateUUID = UUID.randomUUID();
        mockedProject = createProject(UUID.randomUUID(), PROJECT_NAME);
        mockedState = createState(UUID.randomUUID(), STATE_NAME);

        mockedProject = projectRepository.save(mockedProject);
        mockedState = stateRepository.save(mockedState);

        ProjectState projectState = createProjectState(savedProjectStateUUID, mockedProject, mockedState);
        repository.save(projectState);
    }

    @Test
    @DisplayName("Unit Test: Find Project: should find project by UUID")
    public void testFindStateProjectByUUID() {
        Optional<ProjectState> foundProjectState = repository.findByUUID(savedProjectStateUUID);
        assertTrue(foundProjectState.isPresent());
    }

    @Test
    @DisplayName("Unit Test: Find Project: should find State by associated Project and State")
    public void testFindStateProjectByProjectAndState() {
        Optional<ProjectState> foundProjectState = repository.findByProjectAndState(mockedProject, mockedState);
        assertTrue(foundProjectState.isPresent());
    }

    private Project createProject(UUID uuid, String name) {
        return Project.builder()
                .UUID(uuid)
                .name(name)
                .build();
    }

    private State createState(UUID uuid, String name) {
        return State.builder()
                .UUID(uuid)
                .name(name)
                .build();
    }

    private ProjectState createProjectState(UUID uuid, Project project, State state) {
        return ProjectState.builder()
                .UUID(uuid)
                .project(project)
                .state(state)
                .build();
    }
}
