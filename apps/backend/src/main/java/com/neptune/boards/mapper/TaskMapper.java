package com.neptune.boards.mapper;

import com.neptune.boards.dto.task.TaskResponseDTO;
import com.neptune.boards.entity.Task;

public class TaskMapper {
    public static TaskResponseDTO mapBoardToResponseDTO(Task task){
        return TaskResponseDTO.builder()
                .name(task.getName())
                .description(task.getDescription())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .state(task.getState().getName())
                .board(task.getProject().getUUID())
                .build();
    }
}
