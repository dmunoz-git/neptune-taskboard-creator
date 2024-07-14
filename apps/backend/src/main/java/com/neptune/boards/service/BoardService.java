package com.neptune.boards.service;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BoardService implements IBoardService {
    @Autowired
    private BoardRepository repository;

    @Override
    public Board createBoard(UUID uuid, BoardRequestDTO boardRequest) {
        Board board = Board.builder()
                .UUID(uuid)
                .name(boardRequest.getName())
                .description(boardRequest.getDescription())
                .build();
        return this.repository.save(board);
    }

    @Override
    public Board getBoard(UUID uuid) throws NeptuneBoardsException {
        Optional<Board> dashboard = repository.findByUUID(uuid);

        if(dashboard.isEmpty()){
            throw new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        return dashboard.get();
    }

    @Override
    public List<Board> getAllBoards() {
        return repository.findAll();
    }

    @Override
    public Board deleteBoard(UUID uuid) throws NeptuneBoardsException {
        Board dashboard = this.getBoard(uuid);
        this.repository.delete(dashboard);
        return dashboard;
    }


    @Override
    public Board updateBoard(UUID uuid, BoardRequestDTO boardRequest) throws NeptuneBoardsException {
        Board foundedBoard = this.getBoard(uuid);
        Board updatedBoard = foundedBoard.updateFromDto(boardRequest);
        return repository.save(updatedBoard);
    }
}
