package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.entities.Orderstate;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class OrderDTO {

    private Integer id;
    private Cart cart;
    private LocalDateTime orderDate;
    private Orderstate state;
    private String notes;
    private LocalDateTime lastUpdate;

    public OrderDTO() {}

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.cart = order.getCart();
        this.orderDate = order.getOrderDate();
        this.state = order.getState();
        this.notes = order.getNotes();
        this.lastUpdate = order.getLastUpdate();
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", cart=" + cart +
                ", orderDate=" + orderDate +
                ", state=" + state +
                ", notes='" + notes + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderDTO orderDTO = (OrderDTO) o;
        return Objects.equals(id, orderDTO.id) && Objects.equals(orderDate, orderDTO.orderDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderDate);
    }
}
