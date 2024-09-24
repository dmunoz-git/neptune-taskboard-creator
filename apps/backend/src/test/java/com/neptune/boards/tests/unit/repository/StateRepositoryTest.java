package com.neptune.boards.tests.unit.repository;

import com.neptune.boards.entity.State;
import com.neptune.boards.repository.StateRepository;
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
public class StateRepositoryTest {
    private static final String STATE_NAME = "To Do";
    private static final String STATE_DEFINITION_OF_DONE = "Tasks that need to be started";

    private final UUID savedStateUUID = UUID.randomUUID();

    @Autowired
    StateRepository repository;

    @BeforeAll
    public void setUp() {
        State state = createState(savedStateUUID, STATE_NAME, STATE_DEFINITION_OF_DONE);
        repository.save(state);
    }

    @Test
    @DisplayName("Unit Test: Find State: should find a state by UUID")
    public void testFindStateByUUID() {
        Optional<State> foundState = repository.findByUUID(this.savedStateUUID);
        Assertions.assertTrue(foundState.isPresent());
    }

    @Test
    @DisplayName("Unit Test: Find State: should find state by name")
    public void testFindStateByName() {
        Optional<State> foundState = repository.findByName(STATE_NAME);
        Assertions.assertTrue(foundState.isPresent());
    }

    private State createState(UUID uuid, String name, String definitionOfDone) {
        return State.builder()
                .UUID(uuid)
                .name(name)
                .definitionOfDone(definitionOfDone)
                .build();
    }
}
