package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillProcessDTO {

    private Integer billId;
    private Integer paymentTypeId;
    private Integer cartId;

    @Override
    public String toString() {
        return "BillProcessDTO{" +
                "billId=" + billId +
                ", paymentTypeId=" + paymentTypeId +
                ", cartId=" + cartId +
                '}';
    }
}
