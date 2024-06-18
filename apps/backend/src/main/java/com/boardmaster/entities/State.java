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

    public void setName(String name) {
        this.name = name;
    }
}