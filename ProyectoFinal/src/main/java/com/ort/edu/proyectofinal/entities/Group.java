package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GroupId")
    private int groupId;
    @Column(name = "Name")
    private String name;
    @Column(name = "GroupStateId")
    private int groupStateId;
}
