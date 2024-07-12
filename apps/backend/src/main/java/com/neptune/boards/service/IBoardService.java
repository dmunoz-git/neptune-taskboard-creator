package com.neptune.boards.service;

import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.BoardmasterException;

import java.util.List;

public interface IBoardService {
    public Board create(String name);
    public Board getDashboard(Long id) throws BoardmasterException;
    public Board getDashboard(String name) throws BoardmasterException;
    public List<Board> getAllDashboards();
    public Board deleteDashboard(Long id) throws BoardmasterException;
    public Board deleteDashboard(String name) throws BoardmasterException;
    public Board changeDashboardName(String name) throws BoardmasterException;
}
