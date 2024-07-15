package com.neptune.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TaskRequestDTO {
    private String name;
    private String description;
    private UUID board;
}
