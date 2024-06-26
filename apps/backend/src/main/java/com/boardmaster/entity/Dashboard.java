package com.boardmaster.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
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
}