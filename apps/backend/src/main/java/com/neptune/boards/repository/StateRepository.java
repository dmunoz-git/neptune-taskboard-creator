package com.neptune.boards.repository;

import com.neptune.boards.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface StateRepository extends JpaRepository<State, Long> {
    public Optional<State> findByName(String name);
    public Optional<State> findByUUID(UUID uuid);
}
