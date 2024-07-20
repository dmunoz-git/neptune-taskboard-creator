package com.neptune.boards.dto.task;

import com.neptune.boards.dto.state.StateResponseDTO;
import lombok.Data;
import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class TaskResponseDTO {
    private String name;
    private String description;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    // TODO: definitionOfDone and another detail data shouldn't be included into this object
    private StateResponseDTO state;

    private UUID board;
}
