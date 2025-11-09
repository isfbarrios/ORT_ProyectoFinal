package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId")
    private int customerId;
    @Column(name = "SessionId")
    private int sessionId;
    @Column(name = "CreatedDate")
    private Date createdDate;
    @Column(name = "StateId")
    private int stateId;
    @Column(name = "LastUpdate")
    private Date lastUpdate;
}
