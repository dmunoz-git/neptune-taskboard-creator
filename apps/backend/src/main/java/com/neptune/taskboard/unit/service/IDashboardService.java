package com.neptune.taskboard.unit.service;

import com.neptune.taskboard.unit.entity.Dashboard;
import com.neptune.taskboard.unit.exception.BoardmasterException;

import java.util.List;

public interface IDashboardService {
    public Dashboard create(String name);
    public Dashboard getDashboard(Long id) throws BoardmasterException;
    public Dashboard getDashboard(String name) throws BoardmasterException;
    public List<Dashboard> getAllDashboards();
    public Dashboard deleteDashboard(Long id) throws BoardmasterException;
    public Dashboard deleteDashboard(String name) throws BoardmasterException;
    public Dashboard changeDashboardName(String name) throws BoardmasterException;
}
