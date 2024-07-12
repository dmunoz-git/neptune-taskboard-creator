package com.neptune.boards.repository;

import com.neptune.boards.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IBoardRepository extends JpaRepository<Board, Long> {
    public Optional<Board> findByName(String name);
}
