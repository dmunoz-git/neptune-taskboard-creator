package com.boardmaster.repository;

import com.boardmaster.entity.Dashboard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDashboardRepository extends JpaRepository<Dashboard, Long> {
    public Optional<Dashboard> findByName(String name);
}
