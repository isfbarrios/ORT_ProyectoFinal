package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import jakarta.persistence.Table;

@Entity
@Table(name = "menuitemtype", schema = "proyectofinal")
public class Menuitemtype {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TypeId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 80)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("current_timestamp()")
    @Column(name = "CreatedDate", nullable = false)
    private Instant createdDate;

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

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}