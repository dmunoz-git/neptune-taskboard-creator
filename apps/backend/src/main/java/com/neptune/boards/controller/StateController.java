package com.neptune.boards.controller;

import com.neptune.boards.dto.state.StateRequestDTO;
import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.StateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/state")
@Tag(name="States: ")
public class StateController {

    @Autowired
    private StateService service;

    @Operation(summary = "Get a state", description = "Retrieve a state by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "State not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> getStateByUUID(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getState(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Create a state", description = "Create a new state associated with a specific project UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "State created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> createState(@PathVariable UUID uuid, @RequestBody StateRequestDTO stateRequest) {
        return new ResponseEntity<>(service.createState(uuid, stateRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a state", description = "Delete a state identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State deleted successfully"),
            @ApiResponse(responseCode = "404", description = "State not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> deleteState(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.deleteState(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Update a state", description = "Update an existing state identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "State updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "State not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<StateResponseDTO> updateState(@PathVariable UUID uuid, @RequestBody StateRequestDTO stateRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.updateState(uuid, stateRequest), HttpStatus.OK);
    }

    @Operation(summary = "List all states", description = "Retrieve a list of all states")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(path = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<StateResponseDTO>> listStates() {
        return new ResponseEntity<>(service.listStates(), HttpStatus.OK);
    }
}
