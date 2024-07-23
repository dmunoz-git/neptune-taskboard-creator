package com.neptune.boards.mapper;

import com.neptune.boards.dto.state.StateResponseDTO;
import com.neptune.boards.entity.State;

public class StateMapper {
    public static StateResponseDTO mapStateToResponseDTO (State state){
        return StateResponseDTO.builder()
                .uuid(state.getUUID())
                .name(state.getName())
                .definitionOfDone(state.getDefinitionOfDone())
                .build();
    }
}
