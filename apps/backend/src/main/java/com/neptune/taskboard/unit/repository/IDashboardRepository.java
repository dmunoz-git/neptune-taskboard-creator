package com.neptune.taskboard.unit.repository;

import com.neptune.taskboard.unit.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDashboardRepository extends JpaRepository<Dashboard, Long> {
    public Optional<Dashboard> findByName(String name);
}
