package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillRequestDTO {

    private Integer cartId;

    public BillRequestDTO() {}

    public BillRequestDTO(Integer cartId) {
        this.cartId = cartId;
    }

    @Override
    public String toString() {
        return "BillRequestDTO{" +
                "cartId=" + cartId +
                '}';
    }
}
