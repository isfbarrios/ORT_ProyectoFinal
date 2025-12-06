package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TableReservationRequestDTO {

    private int tableId;
    private int shiftId;
    private LocalDate reservationDate;
    private String customerName; // nombre del cliente (no requiere login)
}
