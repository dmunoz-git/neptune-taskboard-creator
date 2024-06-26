package com.boardmaster.entity;

import jakarta.persistence.*;

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