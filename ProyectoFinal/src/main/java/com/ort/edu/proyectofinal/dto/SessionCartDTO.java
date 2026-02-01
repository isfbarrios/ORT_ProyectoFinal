package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class SessionCartDTO {

    private Integer cartId;
    private List<SessionCartItemDTO> items;
    private BigDecimal totalAmount;

    public SessionCartDTO() {}

    public SessionCartDTO(Integer cartId, List<SessionCartItemDTO> items, BigDecimal totalAmount) {
        this.cartId = cartId;
        this.items = items;
        this.totalAmount = totalAmount;
    }
}
