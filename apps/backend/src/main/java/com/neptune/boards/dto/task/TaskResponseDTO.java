package com.neptune.boards.dto.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TaskResponseDTO {
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private String state;
    private UUID board;
}
