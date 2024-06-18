package com.boardmaster.repositories;

import com.boardmaster.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStateRepository extends JpaRepository<State, Long> {
    public Optional<State> findByName(String name);
}
