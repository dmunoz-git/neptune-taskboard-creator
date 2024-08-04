package com.neptune.boards.dto.projectState;

import com.neptune.boards.dto.state.StateRequestDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
public class TaskStateDTO extends StateRequestDTO {
    private UUID stateUUID;
    private boolean defaultState;

    public TaskStateDTO(UUID stateUUID, String name, String definitionOfDone, Boolean defaultState){
        super(name, definitionOfDone);
        this.stateUUID = stateUUID;
        this.defaultState = defaultState;
    }

    public boolean getDefaultState() {
        return this.defaultState;
    }
}
