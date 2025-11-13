package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Orderstate;

import java.util.Objects;

public class OrderStateDTO {

    private Integer id;
    private String name;
    private String description;

    public OrderStateDTO() {}

    public OrderStateDTO(Orderstate orderState) {
        this.id = orderState.getId();
        this.name = orderState.getName();
        this.description = orderState.getDescription();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "OrderStateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        OrderStateDTO that = (OrderStateDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
