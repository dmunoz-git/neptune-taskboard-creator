package com.neptune.boards.entity;

import com.neptune.boards.dto.task.TaskRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID UUID;

    @Column(unique = true, nullable = false)
    private String name;
    private String description;

    private LocalDate createdAt;
    private LocalDate updatedAt;

    @ManyToOne
    @JoinColumn(name = "state_uuid")
    private ProjectState state;

    @ManyToOne
    @JoinColumn(name = "board_uuid")
    private Project project;

    // Life cycle callbacks
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    // Update method from DTO
    public Task updateFromDto(TaskRequestDTO dto) {
        Task.TaskBuilder builder = Task.builder()
                .id(this.id)
                .UUID(this.UUID)
                .name(dto.getName() != null ? dto.getName() : this.name)
                .description(dto.getDescription() != null ? dto.getDescription() : this.description)
                .createdAt(this.createdAt)
                .updatedAt(LocalDate.now());
        return builder.build();
    }
}