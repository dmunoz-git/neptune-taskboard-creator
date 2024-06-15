package com.boardmaster.services;

import java.util.List;

import com.boardmaster.entities.Board;
import com.boardmaster.entities.State;
import com.boardmaster.exceptions.BoardmasterException;

public interface IBoardService {
    public List<Board> listBoards();
    public Board getBoard(Long id) throws BoardmasterException;
    public Board getBoard(String name) throws BoardmasterException;
    public void create(Board board);
    public void delete(Long boardId);
    public void changeName(String name);
    public void addColumn(State state);
}
