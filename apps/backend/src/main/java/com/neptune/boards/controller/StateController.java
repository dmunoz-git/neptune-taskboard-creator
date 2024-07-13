package com.neptune.boards.controller;

import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    private StateService service;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> getStateById(@PathVariable Long id) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getState(id), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> getStateByName(@RequestParam String name) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getState(name), HttpStatus.OK);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> createState(@RequestBody State state) {
        return new ResponseEntity<>(service.create(state), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<State> deleteStateById(@PathVariable Long id) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }

    @PutMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> changeStateName(@RequestParam String currentName, @RequestParam String newName) throws NeptuneBoardsException {
            service.changeName(newName);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Void> updateStateName(@PathVariable Long id, @RequestParam String name) throws NeptuneBoardsException {
        service.updateName(id, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
