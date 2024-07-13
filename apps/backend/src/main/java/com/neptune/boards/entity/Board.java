package com.neptune.boards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;  // Private id for table

    @NotNull
    private UUID UUID; // Board identifier specified by the client

    @Column(unique = true, nullable = false)
    private String name;

    private String description;

    private LocalDate createdAt;

    private LocalDate updatedAt;

    /*
    * TODO: Create an entity to Manage TemplateStates
    *
    * @OneToMany(mappedBy="board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    * private TemplateStates templateStates
    * */

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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
}