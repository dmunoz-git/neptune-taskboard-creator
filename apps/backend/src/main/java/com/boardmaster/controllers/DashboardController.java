package com.boardmaster.controllers;

import com.boardmaster.entities.Dashboard;
import com.boardmaster.exceptions.BoardmasterException;
import com.boardmaster.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService service;

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> createDashboard(@RequestParam String name) {
        return new ResponseEntity<>(service.create(name), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> getDashboardById(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(service.getDashboard(id), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> getDashboardByName(@RequestParam String name) throws BoardmasterException {
        return new ResponseEntity<>(service.getDashboard(name), HttpStatus.OK);
    }

    @GetMapping(path = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Dashboard>> getAllDashboards() {
        return new ResponseEntity<>(service.getAllDashboards(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> deleteDashboardById(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(service.deleteDashboard(id), HttpStatus.OK);
    }

    // Eliminar un Dashboard por nombre
    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> deleteDashboardByName(@RequestParam String name) throws BoardmasterException {
        return new ResponseEntity<>(service.deleteDashboard(name), HttpStatus.OK);
    }

    // Cambiar el nombre de un Dashboard
    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Dashboard> changeDashboardName(@RequestParam String currentName, @RequestParam String newName) throws BoardmasterException {
        return new ResponseEntity<>(service.changeDashboardName(newName), HttpStatus.OK);
    }
}
