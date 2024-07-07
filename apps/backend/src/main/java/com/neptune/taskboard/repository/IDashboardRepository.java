package com.neptune.taskboard.repository;

import com.neptune.taskboard.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDashboardRepository extends JpaRepository<Dashboard, Long> {
    public Optional<Dashboard> findByName(String name);
}
