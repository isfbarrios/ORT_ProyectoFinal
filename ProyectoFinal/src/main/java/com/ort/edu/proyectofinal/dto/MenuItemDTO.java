package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.entities.Menuitemstate;
import com.ort.edu.proyectofinal.entities.Menuitemtype;

import java.math.BigDecimal;
import java.util.Objects;

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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
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

    public Menuitemtype getType() {
        return type;
    }

    public void setType(Menuitemtype type) {
        this.type = type;
    }

    public Menuitemstate getState() {
        return state;
    }

    public void setState(Menuitemstate state) {
        this.state = state;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
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
