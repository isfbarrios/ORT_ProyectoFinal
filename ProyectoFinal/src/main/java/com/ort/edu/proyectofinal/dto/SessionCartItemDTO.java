package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cartitem;
import java.math.BigDecimal;

public class SessionCartItemDTO {

    private Long cartItemId;
    private Integer menuItemId;
    private String name;
    private Integer quantity;
    private BigDecimal unitPrice;

    public SessionCartItemDTO() {
    }

    public SessionCartItemDTO(Cartitem item) {
        this.cartItemId = item.getId().getItemId().longValue();
        this.menuItemId = item.getMenuItem().getMenuItemId();
        this.name = item.getMenuItem().getName();
        this.quantity = item.getQuantity();
        // TODO: si tenés otro campo de precio, ajustá acá
        this.unitPrice = item.getMenuItem().getBasePrice();
    }

    // getters y setters

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }
}
