package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;

@Entity
@Table(name = "paymenttype", schema = "proyectofinal")
public class Paymenttype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PaymentTypeId", nullable = false)
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