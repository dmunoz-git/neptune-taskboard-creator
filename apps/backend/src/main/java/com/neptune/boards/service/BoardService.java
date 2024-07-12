package com.neptune.boards.service;

import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.BoardmasterException;
import com.neptune.boards.repository.IBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private IBoardRepository repository;

    @Override
    public Board create(Board board) {
        return this.repository.save(board);
    }

    @Override
    public Board getDashboard(Long id) throws BoardmasterException {
        Optional<Board> dashboard = repository.findById(id);

        if(dashboard.isEmpty()){
            throw new BoardmasterException("Object not found");
        }

        return dashboard.get();
    }

    @Override
    public Board getDashboard(String name) throws BoardmasterException{
        Optional<Board> dashboard = repository.findByName(name);

        if(dashboard.isEmpty()){
            throw new BoardmasterException("Object not found");
        }

        return dashboard.get();
    }

    @Override
    public List<Board> getAllDashboards() {
        return repository.findAll();
    }

    @Override
    public Board deleteDashboard(Long id) throws BoardmasterException {
        Board dashboard = this.getDashboard(id);
        this.repository.delete(dashboard);
        return dashboard;
    }

    @Override
    public Board deleteDashboard(String name) throws BoardmasterException {
        Board dashboard = this.getDashboard(name);
        this.repository.delete(dashboard);
        return dashboard;
    }

    @Override
    public Board changeDashboardName(String name) throws BoardmasterException {
        Board dashboard = this.getDashboard(name);
        dashboard.setName(name);
        return repository.save(dashboard);
    }
}
