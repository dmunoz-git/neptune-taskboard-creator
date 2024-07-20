package com.neptune.boards.mapper;

import com.neptune.boards.dto.project.ProjectResponseDTO;
import com.neptune.boards.entity.Project;
import com.neptune.boards.entity.Task;

import java.util.Collections;

// TODO: Consider using specialized libraries like MapStruct or ModelMapper for object mapping
public class ProjectMapper {
    public static ProjectResponseDTO mapBoardToResponseDTO(Project project){
        return ProjectResponseDTO.builder()
                .uuid(project.getUUID())
                .name(project.getName())
                .description(project.getDescription())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .tasks(project.getTasks() != null ? project.getTasks().stream().map(Task::getUUID).toList() : Collections.emptyList())
                .build();
    }
}
