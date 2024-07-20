package com.neptune.boards.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.neptune.boards.dto.project.ProjectRequestDTO;
import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.dto.project.ProjectResponseViews;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.ProjectService;
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
@RequestMapping("/api/project")
@Tag(name = "Projects: ")
public class ProjectController {

    @Autowired
    private ProjectService service;

    @Operation(summary = "Create a new project", description = "Create a new project associated with a specific project UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Project created successfully"),
            @ApiResponse(responseCode = "400", description = "Bad Request - Required data is missing or malformed", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - An unexpected error occurred", content = @Content)
    })
    @PostMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonView(ProjectResponseViews.ProjectSummary.class)
    public ResponseEntity<ProjectResponseDTO> createProject(@PathVariable UUID uuid, @RequestBody ProjectRequestDTO boardRequest) {
        return new ResponseEntity<>(service.createBoard(uuid, boardRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a project", description = "Fetches the project details identified by the provided UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - An unexpected error occurred", content = @Content)
    })
    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonView(ProjectResponseViews.ProjectDetail.class)
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getBoard(uuid), HttpStatus.OK);
    }

    @Operation(summary = "List all projects", description = "Retrieves a list of all projects")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Projects retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - An unexpected error occurred", content = @Content)
    })
    @GetMapping(path = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonView(ProjectResponseViews.ProjectDetail.class)
    public ResponseEntity<List<ProjectResponseDTO>> getAllProjects() {
        return new ResponseEntity<>(service.getAllBoards(), HttpStatus.OK);
    }

    @Operation(summary = "Delete a project", description = "Deletes the project identified by the provided UUID along with its associated tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project deleted successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - An unexpected error occurred", content = @Content)
    })
    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonView(ProjectResponseViews.ProjectSummary.class)
    public ResponseEntity<ProjectResponseDTO> deleteProject(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.deleteBoard(uuid), HttpStatus.OK);
    }

    @Operation(summary = "Update a project", description = "Updates the project identified by the provided UUID with the new details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Project updated successfully"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error - An unexpected error occurred", content = @Content)
    })
    @PutMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @JsonView(ProjectResponseViews.ProjectDetail.class)
    public ResponseEntity<ProjectResponseDTO> updateProject(@PathVariable UUID uuid, @RequestBody ProjectRequestDTO boardRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.updateBoard(uuid, boardRequest), HttpStatus.OK);
    }
}
