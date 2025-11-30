package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.entities.Menuitemstate;
import com.ort.edu.proyectofinal.entities.Menuitemtype;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class MenuItemDTO {

    private Integer id;
    private Menu menu;
    private String name;
    private String description;
    private Menuitemtype type;
    private Menuitemstate state;
    private BigDecimal basePrice;

    public MenuItemDTO() {}

    public MenuItemDTO(Menuitem item) {
        this.id = item.getId();
        this.menu = item.getMenu();
        this.name = item.getName();
        this.description = item.getDescription();
        this.type = item.getType();
        this.state = item.getState();
        this.basePrice = item.getBasePrice();
    }

    @Override
    public String toString() {
        return "MenuItemDTO{" +
                "id=" + id +
                ", menu=" + menu +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type=" + type +
                ", state=" + state +
                ", basePrice=" + basePrice +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MenuItemDTO that = (MenuItemDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(menu, that.menu) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menu, name);
    }
}
