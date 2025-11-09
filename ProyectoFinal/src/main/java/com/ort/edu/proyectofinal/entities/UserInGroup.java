package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "useringroup")
public class UserInGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int userId;
    @Column(name = "GroupId")
    private int groupId;
}
