package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "userstate")
public class UserState {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserStateId")
    private int userStateId;
    @Column(name = "Name")
    private String name;
}
