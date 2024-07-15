package com.neptune.boards.mapper;

import com.neptune.boards.dto.BoardResponseDTO;
import com.neptune.boards.entity.Board;
import com.neptune.boards.entity.Task;

public class BoardMapper {
    public static BoardResponseDTO mapBoardToResponseDTO(Board board){
        return BoardResponseDTO.builder()
                .UUID(board.getUUID())
                .name(board.getName())
                .description(board.getDescription())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .tasks(board.getTasks().stream().map(Task::getUUID).toList())
                .build();
    }
}
