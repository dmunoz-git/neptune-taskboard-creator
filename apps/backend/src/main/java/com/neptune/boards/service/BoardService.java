package com.neptune.boards.service;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.dto.BoardResponseDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;
import com.neptune.boards.mapper.BoardMapper;
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
    public BoardResponseDTO createBoard(UUID uuid, BoardRequestDTO boardRequest) {
        // Create board object
        Board board = Board.builder()
                .UUID(uuid)
                .name(boardRequest.getName())
                .description(boardRequest.getDescription())
                .build();

        // Save and autogenerate the rest of values
        Board savedBoard = this.repository.save(board);

        // Map database object to board response
        return BoardMapper.mapBoardToResponseDTO(savedBoard);
    }

    @Override
    public BoardResponseDTO getBoard(UUID uuid) throws NeptuneBoardsException {
        Optional<Board> dashboard = repository.findByUUID(uuid);

        if(dashboard.isEmpty()){
            throw new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Board savedBoard = dashboard.get();

        return BoardMapper.mapBoardToResponseDTO(savedBoard);
    }

    @Override
    public List<BoardResponseDTO> getAllBoards() {
        return repository.findAll().stream().map(BoardMapper::mapBoardToResponseDTO).toList();
    }

    @Override
    public BoardResponseDTO deleteBoard(UUID uuid) throws NeptuneBoardsException {
        Optional<Board> board = repository.findByUUID(uuid);

        if(board.isEmpty()){
            throw new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        this.repository.delete(board.get());

        return BoardMapper.mapBoardToResponseDTO(board.get());
    }


    @Override
    public BoardResponseDTO updateBoard(UUID uuid, BoardRequestDTO boardRequest) throws NeptuneBoardsException {
        Optional<Board> foundBoard = repository.findByUUID(uuid);

        if(foundBoard.isEmpty()){
            throw new NeptuneBoardsException("Board not found", HttpStatus.NOT_FOUND, this.getClass());
        }

        Board updatedBoard = foundBoard.get().updateFromDto(boardRequest);

        // Save updated board
        Board savedBoard = repository.save(updatedBoard);

        return BoardMapper.mapBoardToResponseDTO(savedBoard);
    }
}
