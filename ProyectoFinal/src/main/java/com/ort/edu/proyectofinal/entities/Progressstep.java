package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

@Entity
@Table(name = "progresssteps", schema = "proyectofinal")
public class Progressstep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "StepId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 80)
    private String name;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("0")
    @Column(name = "PreparationTime", nullable = false)
    private Integer preparationTime;

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

    public Integer getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Integer preparationTime) {
        this.preparationTime = preparationTime;
    }

}