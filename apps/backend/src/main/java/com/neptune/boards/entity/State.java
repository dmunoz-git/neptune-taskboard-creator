package com.neptune.boards.entity;

import com.neptune.boards.dto.BoardRequestDTO;
import com.neptune.boards.dto.StateRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@AllArgsConstructor
@Builder
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID UUID;

    @Column(unique = true, nullable = false)
    private String name;

    private String dod; // Definition of Done (DoD)

    public State updateFromDto(StateRequestDTO dto) {
        State.StateBuilder builder = State.builder()
                .id(this.id)
                .UUID(this.UUID)
                .name(dto.getName() != null ? dto.getName() : this.name)
                .dod(dto.getDod() != null ? dto.getDod() : this.dod);
        return builder.build();
    }
}