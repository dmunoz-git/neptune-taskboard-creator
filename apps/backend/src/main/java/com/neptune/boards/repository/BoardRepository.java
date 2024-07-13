package com.neptune.boards.repository;

import com.neptune.boards.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BoardRepository extends JpaRepository<Board, Long> {
    public Optional<Board> findByUUID(UUID uuid);
    public Optional<Board> deleteByUUID(UUID uuid);
}
