package com.neptune.boards.dto.projectState;

import com.neptune.boards.dto.state.StateRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class TaskStateDTO extends StateRequestDTO {
    private UUID stateUUID;
    private boolean defaultState;

    public boolean getDefaultState() {
        return this.defaultState;
    }
}
