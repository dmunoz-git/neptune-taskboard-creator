package com.neptune.boards.controller;

import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.dto.TaskResponseDTO;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.TaskService;
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
@RequestMapping("/api/task")
@Tag(name="Tasks: ")
public class TaskController {

    @Autowired
    private TaskService service;

    @Operation(summary = "Get a task", description = "Retrieve a task by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskResponseDTO> getTaskById(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getTask(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Create a task", description = "Create a new task associated with a specific project UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PostMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskResponseDTO> createTask(@PathVariable UUID uuid, @RequestBody TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.createTask(uuid, taskRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a task", description = "Update an existing task identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid input", content = @Content),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @PutMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable UUID uuid, @RequestBody TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.updateTask(uuid, taskRequest), HttpStatus.OK);
    }

    @Operation(summary = "Delete a task", description = "Delete a task identified by its UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<TaskResponseDTO> deleteTask(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.deleteTask(uuid), HttpStatus.OK);
    }

    @Operation(summary = "List all tasks", description = "Retrieve a list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping(path="/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<TaskResponseDTO>> listTasks() {
        return new ResponseEntity<>(service.listTasks(), HttpStatus.OK);
    }
}
