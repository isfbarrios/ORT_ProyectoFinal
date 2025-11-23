package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "menu", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MenuId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 120)
    private String name;

    @Column(name = "Active", nullable = false)
    private boolean active;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("curdate()")
    @Column(name = "Date", nullable = false)
    private LocalDate date;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;
}