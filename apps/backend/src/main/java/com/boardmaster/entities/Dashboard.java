package com.boardmaster.entities;

import jakarta.persistence.*;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    //Constructors
    public Dashboard(String name){
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }

    //Getters and setters
    public void setName(String name){
        this.name = name;
    }
}