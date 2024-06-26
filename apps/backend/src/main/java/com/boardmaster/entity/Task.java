package com.boardmaster.entity;

import jakarta.persistence.*;

@Entity
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "state_id")
    private State state;

    @ManyToOne
    @JoinColumn(name = "dashboard_id")
    private Dashboard dashboard;

    public void setDashboard(Dashboard dashboard){
        this.dashboard = dashboard;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getName(){
        return this.name;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public State getState() {
        return state;
    }
}