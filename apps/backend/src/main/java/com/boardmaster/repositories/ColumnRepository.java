package com.boardmaster.repositories;

import com.boardmaster.entities.Column;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColumnRepository extends JpaRepository<Column, Long> {
}
