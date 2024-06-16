package com.boardmaster.services;

import com.boardmaster.BoardMasterApplication;
import com.boardmaster.entities.Dashboard;
import com.boardmaster.exceptions.BoardmasterException;

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
