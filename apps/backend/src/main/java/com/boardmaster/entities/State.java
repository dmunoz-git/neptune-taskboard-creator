package com.boardmaster.entities;

import java.util.List;
import jakarta.persistence.*;


@Entity
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @OneToMany(mappedBy = "state", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;
}