package com.testSpring1.questDataTest.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Fire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int severity;
    Instant date;

    @ManyToOne
    Fireman fireman;

    public Fire() {
        this.date = Instant.now();
    }

    public Fireman getFireman() {
        return fireman;
    }

    public Fireman setFireman(Fireman fireman) {
        this.fireman = fireman;
        return fireman;
    }

    public Fire(Long id, int severity, Instant date) {
        this.id = id;
        this.severity = severity;
        this.date = date;
    }

    public Fire(int severity, Instant date) {
        this.severity = severity;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public Long setId(Long id) {
        this.id = id;
        return id;
    }

    public int getSeverity() {
        return severity;
    }

    public int setSeverity(int severity) {
        this.severity = severity;
        return severity;
    }

    public Instant getDate() {
        return date;
    }

    public Instant setDate(Instant date) {
        this.date = date;
        return date;
    }
}
