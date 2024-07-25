package com.neptune.boards.entity;

import com.neptune.boards.dto.state.StateRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID UUID;

    @Column(unique = true, nullable = false)
    private String name;

    private String definitionOfDone; // Definition of Done (DoD)

    public State updateFromDto(StateRequestDTO dto) {
        State.StateBuilder builder = State.builder()
                .id(this.id)
                .UUID(this.UUID)
                .name(dto.getName() != null ? dto.getName() : this.name)
                .definitionOfDone(dto.getDefinitionOfDone() != null ? dto.getDefinitionOfDone() : this.definitionOfDone);
        return builder.build();
    }
}