package com.neptune.boards.dto.project;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ProjectRequestDTO {
    private String name;
    private String description;
}
