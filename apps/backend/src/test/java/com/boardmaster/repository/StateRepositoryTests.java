package com.boardmaster.repository;

import com.boardmaster.entities.State;
import com.boardmaster.repositories.IStateRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class StateRepositoryTests {
    @Autowired
    IStateRepository repository;

    @Test
    @DisplayName("State Repository Unit Test: should be saved in db")
    public void saveStateTest() {
        State state = new State();
        state.setName("Test");

        State savedStated = repository.save(state);

        Assertions.assertNotNull(savedStated);
    }
}
