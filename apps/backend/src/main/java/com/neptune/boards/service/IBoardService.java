package com.neptune.boards.service;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.dto.BoardResponseDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.exception.NeptuneBoardsException;

import java.util.List;
import java.util.UUID;

public interface IBoardService {
    public BoardResponseDTO createBoard(UUID uuid, BoardRequestDTO requestDTO);
    public BoardResponseDTO getBoard(UUID uuid) throws NeptuneBoardsException;
    public List<BoardResponseDTO> getAllBoards();
    public BoardResponseDTO deleteBoard(UUID uuid) throws NeptuneBoardsException;
    public BoardResponseDTO updateBoard(UUID uuid, BoardRequestDTO dto) throws NeptuneBoardsException;
}
