package com.neptune.boards.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Setter
@Getter
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private UUID UUID;

    @Column(unique = true, nullable = false)
    private String name;

    private String description;
}