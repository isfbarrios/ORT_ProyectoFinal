package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cartstate;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class CartStateDTO {

    private Integer id;
    private String name;
    private String description;

    public CartStateDTO() {}

    public CartStateDTO(Cartstate state) {
        this.id = state.getId();
        this.name = state.getName();
        this.description = state.getDescription();
    }

    @Override
    public String toString() {
        return "CartStateDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartStateDTO that = (CartStateDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }
}
