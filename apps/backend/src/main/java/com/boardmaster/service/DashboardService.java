package com.boardmaster.service;

import com.boardmaster.entity.Dashboard;
import com.boardmaster.exception.BoardmasterException;
import com.boardmaster.repository.IDashboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DashboardService implements IDashboardService{
    @Autowired
    private IDashboardRepository repository;

    @Override
    public Dashboard create(String name) {
        return this.repository.save(new Dashboard(name));
    }

    @Override
    public Dashboard getDashboard(Long id) throws BoardmasterException{
        Optional<Dashboard> dashboard = repository.findById(id);

        if(dashboard.isEmpty()){
            throw new BoardmasterException("Object not found");
        }

        return dashboard.get();
    }

    @Override
    public Dashboard getDashboard(String name) throws BoardmasterException{
        Optional<Dashboard> dashboard = repository.findByName(name);

        if(dashboard.isEmpty()){
            throw new BoardmasterException("Object not found");
        }

        return dashboard.get();
    }

    @Override
    public List<Dashboard> getAllDashboards() {
        return repository.findAll();
    }

    @Override
    public Dashboard deleteDashboard(Long id) throws BoardmasterException {
        Dashboard dashboard = this.getDashboard(id);
        this.repository.delete(dashboard);
        return dashboard;
    }

    @Override
    public Dashboard deleteDashboard(String name) throws BoardmasterException {
        Dashboard dashboard = this.getDashboard(name);
        this.repository.delete(dashboard);
        return dashboard;
    }

    @Override
    public Dashboard changeDashboardName(String name) throws BoardmasterException {
        Dashboard dashboard = this.getDashboard(name);
        dashboard.setName(name);
        return repository.save(dashboard);
    }
}
