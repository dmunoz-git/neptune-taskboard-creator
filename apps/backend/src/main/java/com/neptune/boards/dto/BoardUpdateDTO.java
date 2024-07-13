package com.neptune.boards.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BoardUpdateDTO {
    private String name;
    private String description;
}
