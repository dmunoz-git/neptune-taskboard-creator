package com.neptune.boards.controller;

import com.neptune.boards.dto.StateRequestDTO;
import com.neptune.boards.dto.StateResponseDTO;
import com.neptune.boards.entity.State;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/state")
public class StateController {

    @Autowired
    private StateService service;

    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> getStateByUUID(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getState(uuid), HttpStatus.OK);
    }

    @PostMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> createState(@PathVariable UUID uuid, @RequestBody StateRequestDTO stateRequest) {
        return new ResponseEntity<>(service.createState(uuid, stateRequest), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> deleteState(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.deleteState(uuid), HttpStatus.OK);
    }

    @PutMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> updateState(@PathVariable UUID uuid, @RequestBody StateRequestDTO stateRequest) throws NeptuneBoardsException {
            return new ResponseEntity<>(service.updateState(uuid, stateRequest), HttpStatus.OK);
    }

    @GetMapping(path = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<StateResponseDTO>> listStates(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.listStates(), HttpStatus.OK);
    }
}
