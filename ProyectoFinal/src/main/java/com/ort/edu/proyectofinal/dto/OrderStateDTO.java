package com.ort.edu.proyectofinal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ort.edu.proyectofinal.entities.Orderstate;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class OrderStateDTO {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String name;
    @JsonIgnore
    private String description;

    public OrderStateDTO() {}

    public OrderStateDTO(Orderstate orderState) {
        this.id = orderState.getId();
        this.name = orderState.getName();
        this.description = orderState.getDescription();
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
