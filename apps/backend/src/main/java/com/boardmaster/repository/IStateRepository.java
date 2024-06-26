package com.boardmaster.repository;

import com.boardmaster.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IStateRepository extends JpaRepository<State, Long> {
    public Optional<State> findByName(String name);
}
