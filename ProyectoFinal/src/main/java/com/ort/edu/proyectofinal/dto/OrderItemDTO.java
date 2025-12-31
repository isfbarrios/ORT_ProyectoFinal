package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private OrderitemId id;
    private Cartitem cartItem;
    private Integer quantity;

    public OrderItemDTO() {}

    public OrderItemDTO(Orderitem item) {
        this.id = item.getId();
        this.cartItem = item.getCartItem();
        this.quantity = item.getQuantity();
    }
}
