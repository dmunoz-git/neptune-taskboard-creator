package com.neptune.taskboard.unit.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Dashboard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @OneToMany(mappedBy = "dashboard", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasks;

    //Constructors
    public Dashboard(String name){
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }
}