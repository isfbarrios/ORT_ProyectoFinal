package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "userstate", schema = "proyectofinal")
public class Userstate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserStateId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 50)
    private String name;

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

}