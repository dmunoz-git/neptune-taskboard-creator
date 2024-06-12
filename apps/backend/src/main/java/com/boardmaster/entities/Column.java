package com.boardmaster.entities;

import jakarta.persistence.*;
import org.springframework.scheduling.config.Task;

import java.util.List;

public class Column {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "column", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}