package com.neptune.boards.dto.project;

import com.neptune.boards.dto.projectState.TaskStateDTO;
import lombok.Data;
import lombok.Builder;

import java.util.List;

@Data
@Builder
public class ProjectRequestDTO {
    private String name;
    private String description;
    private List<TaskStateDTO> allowedTaskStates;
}
