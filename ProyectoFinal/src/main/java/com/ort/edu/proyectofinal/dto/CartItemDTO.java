package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import com.ort.edu.proyectofinal.entities.Menuitem;

import java.math.BigDecimal;
import java.util.Objects;

public class CartItemDTO {

    private CartitemId id;
    private Menuitem menuItem;
    private Integer quantity;
    private BigDecimal itemAmount;
    private Integer delayTime;

    public CartItemDTO() {}

    public CartItemDTO(Cartitem item) {
        this.id = item.getId();
        this.menuItem = item.getMenuItem();
        this.quantity = item.getQuantity();
        this.itemAmount = item.getItemAmount();
        this.delayTime = item.getDelayTime();
    }

    public CartitemId getId() {
        return id;
    }

    public void setId(CartitemId id) {
        this.id = id;
    }

    public Menuitem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menuitem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "id=" + id +
                ", menuItem=" + menuItem +
                ", quantity=" + quantity +
                ", itemAmount=" + itemAmount +
                ", delayTime=" + delayTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartItemDTO that = (CartItemDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(menuItem, that.menuItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, menuItem);
    }
}
