package com.neptune.boards.dto.state;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class StateRequestDTO {
    public String name;
    public String dod;
}
