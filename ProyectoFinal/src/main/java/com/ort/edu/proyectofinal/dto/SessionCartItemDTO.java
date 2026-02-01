package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cartitem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SessionCartItemDTO {

    private Integer cartItemId;
    private Integer menuItemId;
    private String name;
    private Integer processed;
    private Integer quantity;
    private BigDecimal unitPrice;

    public SessionCartItemDTO() {}

    public SessionCartItemDTO(Cartitem item) {
        this.cartItemId = item.getId().getCartId();
        this.name = item.getMenuItem().getName();
        this.quantity = item.getQuantity();
        this.processed = item.getProcessed();
        // TODO: si tenés otro campo de precio, ajustá acá
        this.unitPrice = item.getMenuItem().getBasePrice();
    }
}
