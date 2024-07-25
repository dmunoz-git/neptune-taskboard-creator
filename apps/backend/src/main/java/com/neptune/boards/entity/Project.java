package com.neptune.boards.entity;

import com.neptune.boards.dto.project.ProjectRequestDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Private id for table

    @Column(unique = true, nullable = false)
    private UUID UUID; // Project identifier specified by the client

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProjectState> taskStates;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

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
    public Project updateFromDto(ProjectRequestDTO dto) {
        ProjectBuilder builder = Project.builder()
                .id(this.id)
                .UUID(this.UUID)
                .name(dto.getName() != null ? dto.getName() : this.name)
                .description(dto.getDescription() != null ? dto.getDescription() : this.description)
                .createdAt(this.createdAt)
                .updatedAt(LocalDate.now());
        return builder.build();
    }
}