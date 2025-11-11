package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "tablestate", schema = "proyectofinal")
public class Tablestate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StateId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

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

}