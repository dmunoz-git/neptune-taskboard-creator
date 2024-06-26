package com.boardmaster.service;

import com.boardmaster.entity.Dashboard;
import com.boardmaster.exception.BoardmasterException;

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
