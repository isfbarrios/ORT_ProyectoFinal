package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrderUpdateDTO {

    private int orderId;
    private int orderStateId;

    public OrderUpdateDTO() {}

    public OrderUpdateDTO(int orderId, int orderStateId) {
        this.orderId = orderId;
        this.orderStateId = orderStateId;
    }

    @Override
    public String toString() {
        return "OrderUpdateDTO{" +
                "orderId=" + orderId +
                ", orderStateId=" + orderStateId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderUpdateDTO that = (OrderUpdateDTO) o;
        return orderId == that.orderId && orderStateId == that.orderStateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, orderStateId);
    }
}
