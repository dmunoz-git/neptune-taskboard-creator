package com.neptune.taskboard.unit.controller;

import com.neptune.taskboard.unit.entity.Task;
import com.neptune.taskboard.unit.exception.BoardmasterException;
import com.neptune.taskboard.unit.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> getTaskById(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(taskService.getTask(id), HttpStatus.OK);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> createTask(@RequestParam Long dashboardId, @RequestBody Task task) throws BoardmasterException {
        return new ResponseEntity<>(taskService.create(dashboardId, task), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody Task task) throws BoardmasterException {
        return new ResponseEntity<>(taskService.update(id, task), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Task> deleteTask(@PathVariable Long id) throws BoardmasterException {
        return new ResponseEntity<>(taskService.delete(id), HttpStatus.OK);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Task>> listTasks() {
        return new ResponseEntity<>(taskService.listTasks(), HttpStatus.OK);
    }
}
