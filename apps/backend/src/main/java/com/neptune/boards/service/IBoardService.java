package com.neptune.boards.service;

import com.neptune.boards.dto.BoardUpdateDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;

public interface IBoardService {
    public Board createBoard(Board board);
    public Board getBoard(UUID uuid) throws NeptuneBoardsException;
    public List<Board> getAllBoards();
    public Board deleteBoard(UUID uuid) throws NeptuneBoardsException;
    public Board updateBoard(UUID uuid, BoardUpdateDTO dto) throws NeptuneBoardsException;
}
