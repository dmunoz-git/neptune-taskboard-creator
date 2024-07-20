package com.neptune.boards.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StateResponseDTO {
    public UUID uuid;
    public String name;
    public String definitionOfDone;
}
