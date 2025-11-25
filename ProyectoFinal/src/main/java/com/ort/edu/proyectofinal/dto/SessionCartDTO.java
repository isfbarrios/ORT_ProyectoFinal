package com.ort.edu.proyectofinal.dto;

import java.math.BigDecimal;
import java.util.List;

public class SessionCartDTO {

    private List<SessionCartItemDTO> items;
    private BigDecimal totalAmount;

    public SessionCartDTO() {}

    public SessionCartDTO(List<SessionCartItemDTO> items, BigDecimal totalAmount) {
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public List<SessionCartItemDTO> getItems() {
        return items;
    }

    public void setItems(List<SessionCartItemDTO> items) {
        this.items = items;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
