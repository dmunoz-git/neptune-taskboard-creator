package com.neptune.boards.controller;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/board")
public class BoardController {
    @Autowired
    private BoardService service;

    @PostMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Board> createBoard(@PathVariable UUID uuid, @RequestBody BoardRequestDTO requestDTO) {
        return new ResponseEntity<>(service.createBoard(uuid, requestDTO), HttpStatus.CREATED);
    }

    @GetMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Board> getBoard(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.getBoard(uuid), HttpStatus.OK);
    }

    @GetMapping(path = "/list", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Board>> getAllDashboards() {
        return new ResponseEntity<>(service.getAllBoards(), HttpStatus.OK);
    }

    @DeleteMapping(path = "/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Board> deleteDashboardById(@PathVariable UUID uuid) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.deleteBoard(uuid), HttpStatus.OK);
    }

    // Cambiar el nombre de un Dashboard
    @PutMapping(path="/{uuid}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Board> updateBoard(@PathVariable UUID uuid, @RequestBody BoardRequestDTO dto) throws NeptuneBoardsException {
        return new ResponseEntity<>(service.updateBoard(uuid, dto), HttpStatus.OK);
    }
}
