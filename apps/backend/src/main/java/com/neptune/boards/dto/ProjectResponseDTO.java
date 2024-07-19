package com.neptune.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProjectResponseDTO {
    private UUID UUID;
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private List<UUID> tasks;
}
