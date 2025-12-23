package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "ProgressSteps", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
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
}