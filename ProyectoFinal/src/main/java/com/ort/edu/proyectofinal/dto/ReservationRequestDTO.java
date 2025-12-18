package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
public class ReservationRequestDTO {
    private int tableId;
    private int shiftId;
    private LocalDate date;
    private String customerName;

    public ReservationRequestDTO() {
    }

    public ReservationRequestDTO(int tableId, int shiftId, LocalDate date, String customerName) {
        this.tableId = tableId;
        this.shiftId = shiftId;
        this.date = date;
        this.customerName = customerName;
    }

    @Override
    public String toString() {
        return "ReservationRequestDTO{" +
                "tableId=" + tableId +
                ", shiftId=" + shiftId +
                ", date=" + date +
                ", customerName='" + customerName + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequestDTO that = (ReservationRequestDTO) o;
        return tableId == that.tableId && shiftId == that.shiftId
                && Objects.equals(date, that.date) && Objects.equals(customerName, that.customerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, shiftId, date, customerName);
    }
}
