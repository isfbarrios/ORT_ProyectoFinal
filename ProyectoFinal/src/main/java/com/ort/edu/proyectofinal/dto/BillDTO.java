package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Bill;
import com.ort.edu.proyectofinal.entities.Paymenttype;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class BillDTO {

    private Integer id;
    private String billNumber;
    private BigDecimal amount;
    private LocalDateTime date;
    private Paymenttype paymentType;
    private String extraData;

    public BillDTO() {}

    public BillDTO(Bill bill) {
        this.id = bill.getId();
        this.billNumber = bill.getBillNumber();
        this.amount = bill.getAmount();
        this.date = bill.getDate();
        this.paymentType = bill.getPaymentType();
        this.extraData = bill.getExtraData();
    }
}
