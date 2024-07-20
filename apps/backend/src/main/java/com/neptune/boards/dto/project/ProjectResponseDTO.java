package com.neptune.boards.dto.project;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProjectResponseDTO {

    @JsonView(ProjectResponseViews.Summary.class)
    private UUID uuid;

    @JsonView(ProjectResponseViews.Summary.class)
    private String name;

    @JsonView(ProjectResponseViews.Summary.class)
    private String description;

    @JsonView(ProjectResponseViews.Summary.class)
    private LocalDate createdAt;

    @JsonView(ProjectResponseViews.Detail.class)
    private LocalDate updatedAt;

    @JsonView(ProjectResponseViews.Detail.class)
    private List<UUID> tasks;
}
