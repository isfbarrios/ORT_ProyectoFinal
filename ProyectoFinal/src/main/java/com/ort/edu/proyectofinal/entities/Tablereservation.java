package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "TableReservation", schema = "proyectofinal")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Tablereservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ReservationId", nullable = false)
    private Integer id;

    @Column(name = "TableId", nullable = false)
    private Integer tableId;

    @Column(name = "ShiftId", nullable = false)
    private Integer shiftId;

    @Column(name = "CustomerName", nullable = false, length = 30)
    private String customerName;

    @Column(name = "ReservationDate", nullable = false)
    private LocalDate reservationDate;

    @Column(name = "CreatedDate", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "LastUpdate", nullable = false)
    private LocalDateTime lastUpdate;
}
