package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import com.ort.edu.proyectofinal.entities.Menuitem;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
public class CartItemDTO {

    private CartitemId id;
    private Menuitem menuItem;
    private Integer processed;
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
        this.processed = item.getProcessed();
    }

    @Override
    public String toString() {
        return "CartItemDTO{" +
                "id=" + id +
                ", menuItem=" + menuItem +
                ", processed=" + processed +
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
