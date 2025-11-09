package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "groupstate")
public class GroupState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupStateId")
    private int groupStateId;
    @Column(name = "Name")
    private String name;
}
