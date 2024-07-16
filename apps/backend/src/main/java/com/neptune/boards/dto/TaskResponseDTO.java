package com.neptune.boards.dto;

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
    private UUID state;
    private UUID board;
}
