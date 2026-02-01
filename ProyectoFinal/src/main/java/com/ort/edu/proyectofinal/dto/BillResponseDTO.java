package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Bill;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillResponseDTO {

    private Integer billId;
    private String billNumber;
    private BigDecimal amount;
    private LocalDateTime date;


    public BillResponseDTO() {}

    public BillResponseDTO(Bill bill) {
        this.billId = bill.getId();
        this.billNumber = bill.getBillNumber();
        this.amount = bill.getAmount();
        this.date = bill.getDate();
    }
}
