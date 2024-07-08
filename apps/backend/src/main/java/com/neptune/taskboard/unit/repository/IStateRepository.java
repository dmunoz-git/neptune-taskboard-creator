package com.neptune.taskboard.unit.repository;

import com.neptune.taskboard.unit.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStateRepository extends JpaRepository<State, Long> {
    public Optional<State> findByName(String name);
}
