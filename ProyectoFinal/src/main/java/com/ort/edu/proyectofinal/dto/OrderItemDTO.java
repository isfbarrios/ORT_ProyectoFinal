package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDTO {

    private Integer id;
    private Order order;
    private Product product;
    private Integer quantity;
    private java.math.BigDecimal unitPrice;
    private String notes;

    public OrderItemDTO() {}

    public OrderItemDTO(Orderitem item) {
        this.id = item.getId();
        this.order = item.getOrder();
        this.product = item.getProduct();
        this.quantity = item.getQuantity();
        this.unitPrice = item.getUnitPrice();
        this.notes = item.getNotes();
    }
}
