package com.neptune.boards.dto.state;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StateRequestDTO {
    public String name;
    public String definitionOfDone;
}
