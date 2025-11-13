package com.ort.edu.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.entities.Ordercanal;
import com.ort.edu.proyectofinal.entities.Orderstate;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDTO {

    private Integer id;
    private String orderNumber;
    private BigDecimal amount;
    private Orderstate state;
    private Ordercanal canal;
    private String description;

    public OrderDTO() {}

    public OrderDTO(Order order) {
        this.id = order.getId();
        this.orderNumber = order.getOrderNumber();
        this.amount = order.getAmount();
        this.state = order.getState();
        this.canal = order.getCanal();
        this.description = order.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Orderstate getState() {
        return state;
    }

    public void setState(Orderstate state) {
        this.state = state;
    }

    public Ordercanal getCanal() {
        return canal;
    }

    public void setCanal(Ordercanal canal) {
        this.canal = canal;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
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
