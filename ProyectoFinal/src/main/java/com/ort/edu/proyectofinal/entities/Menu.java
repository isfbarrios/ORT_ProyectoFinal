package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "menu", schema = "proyectofinal")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("curdate()")
    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}