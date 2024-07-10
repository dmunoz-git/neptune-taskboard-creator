package com.neptune.taskboard.test.unit.repository;

import com.neptune.taskboard.entity.State;
import com.neptune.taskboard.repository.IStateRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StateRepositoryTest {
    private State savedState;

    @Autowired
    IStateRepository repository;

    @Autowired
    EntityManager entityManager;

    @BeforeEach
    public void setUp() {
        State state = new State();
        state.setName("Test State");
        this.savedState = repository.save(state);
    }


    @Test
    @DisplayName("Create: should create an state in the database")
    public void saveStateTest() {
        Assertions.assertEquals("Test State", this.savedState.getName());
    }

    @Test
    @DisplayName("Exception: should not be able to create a state with the same name")
    public void saveDuplicateStateNameTest() {
        State duplicateState = new State();
        duplicateState.setName("Test State");

        PersistenceException exception = Assertions.assertThrows(PersistenceException.class, () -> {
            repository.saveAndFlush(duplicateState);
            entityManager.flush();
        });

        Assertions.assertTrue(exception.getMessage().contains("ConstraintViolationException"));
    }

    @Test
    @DisplayName("Find: should find a state by its id")
    public void findStateByIdTest() {
        Optional<State> foundState = repository.findById(savedState.getId());

        Assertions.assertTrue(foundState.isPresent());
        Assertions.assertEquals(savedState.getId(), foundState.get().getId());
    }

    @Test
    @DisplayName("Find: should find state by its name")
    public void findStateByNameTest() {
        Optional<State> foundState = repository.findByName("Test State");

        Assertions.assertTrue(((Optional<?>) foundState).isPresent());
        Assertions.assertEquals("Test State", foundState.get().getName());
    }

    @Test
    @DisplayName("Update: should update the name of a state")
    public void updateStateTest() {
        State state = new State();
        state.setName("Test State");

        State savedState = repository.save(state);
        savedState.setName("Updated State");

        State updatedState = repository.save(savedState);

        Assertions.assertNotNull(updatedState);
        Assertions.assertEquals("Updated State", updatedState.getName());
    }

    @Test
    @DisplayName("Delete: should delete a state")
    public void deleteStateByIdTest() {
        repository.deleteById(savedState.getId());

        Optional<State> foundState = repository.findById(savedState.getId());

        Assertions.assertFalse(foundState.isPresent());
    }
}
