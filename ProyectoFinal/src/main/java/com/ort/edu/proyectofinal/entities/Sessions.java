package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "sessions")
public class Sessions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SessionId")
    private int sessionId;
    @Column(name = "CreatedDate")
    private Date createdDate;
}
