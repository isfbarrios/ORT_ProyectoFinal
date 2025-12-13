package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Menu;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class MenuDTO {

    private Integer id;
    private String name;
    private String description;

    public MenuDTO() {}

    public MenuDTO(Menu menu) {
        this.id = menu.getId();
        this.name = menu.getName();
        this.description = menu.getDescription();
    }

    @Override
    public String toString() {
        return "MenuDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuDTO menuDTO = (MenuDTO) o;
        return Objects.equals(id, menuDTO.id) && Objects.equals(name, menuDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
