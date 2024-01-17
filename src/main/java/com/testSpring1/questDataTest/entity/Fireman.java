package com.testSpring1.questDataTest.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Fireman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;

    @OneToMany(mappedBy = "fireman")
    List<Fire> fires;


    public Fireman(String name) {
        this.name = name;
        this.fires = new ArrayList<>();
    }

    public Fireman() {
        this.fires = new ArrayList<>();
    }

    public List<Fire> getFires() {
        return fires;
    }

    public Fireman(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Long setId(Long id) {
        this.id = id;
        return id;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }
}
