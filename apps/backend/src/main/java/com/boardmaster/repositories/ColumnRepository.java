package com.boardmaster.repositories;

import com.boardmaster.entities.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<State, Long> {
}
