package com.boardmaster.controller;

import com.boardmaster.entities.State;
import com.boardmaster.exceptions.BoardmasterException;
import com.boardmaster.services.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/state")
public class StateController {

    @Autowired
    private StateService service;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> getStateById(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(service.getState(id), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> getStateByName(@RequestParam String name) throws BoardmasterException {
        return new ResponseEntity<>(service.getState(name), HttpStatus.OK);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> createState(@RequestBody State state) {
        return new ResponseEntity<>(service.create(state), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> deleteStateById(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeStateName(@RequestParam String currentName, @RequestParam String newName) throws BoardmasterException {
            service.changeName(newName);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateStateName(@PathVariable Long id, @RequestParam String name) throws BoardmasterException {
        service.updateName(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
