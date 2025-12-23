package com.ort.edu.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.entities.Ordercanal;
import com.ort.edu.proyectofinal.entities.Orderstate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class OrderDTO {

    private Integer id;
    private String orderNumber;
    private Cart cart;
    private BigDecimal amount;
    private Orderstate state;
    private Ordercanal canal;
    private String description;

    public OrderDTO() {}

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.cart = order.getCart();
        this.amount = order.getAmount();
        this.state = order.getState();
        this.canal = order.getCanal();
        this.description = order.getDescription();
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", cart=" + cart +
                ", amount=" + amount +
                ", state=" + state +
                ", canal=" + canal +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) && Objects.equals(orderNumber, orderDTO.orderNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderNumber);
    }
}
