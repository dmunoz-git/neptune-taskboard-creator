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

    @JsonView(ProjectResponseViews.ProjectSummary.class)
    private UUID uuid;

    @JsonView(ProjectResponseViews.ProjectSummary.class)
    private String name;

    @JsonView(ProjectResponseViews.ProjectSummary.class)
    private String description;

    @JsonView(ProjectResponseViews.ProjectSummary.class)
    private LocalDate createdAt;

    @JsonView(ProjectResponseViews.ProjectDetail.class)
    private LocalDate updatedAt;

    @JsonView(ProjectResponseViews.ProjectDetail.class)
    private List<UUID> tasks;
}
