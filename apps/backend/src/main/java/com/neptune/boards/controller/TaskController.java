package com.neptune.boards.controller;

import com.neptune.boards.dto.TaskRequestDTO;
import com.neptune.boards.entity.Task;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> getTaskById(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(taskService.getTask(uuid), HttpStatus.OK);
    }

    @PostMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> createTask(@PathVariable UUID uuid, @RequestBody TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(taskService.createTask(uuid, taskRequest), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> updateTask(@PathVariable UUID uuid, @RequestBody TaskRequestDTO taskRequest) throws NeptuneBoardsException {
        return new ResponseEntity<>(taskService.updateTask(uuid, taskRequest), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> deleteTask(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(taskService.deleteTask(uuid), HttpStatus.OK);
    }

    @GetMapping(path="/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Task>> listTasks() {
        return new ResponseEntity<>(taskService.listTasks(), HttpStatus.OK);
    }
}
