package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class CartitemId implements Serializable {
    private static final long serialVersionUID = -8782270964561977868L;
    @Column(name = "CartId", nullable = false)
    private Integer cartId;

    @Column(name = "ItemId", nullable = false)
    private Integer itemId;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CartitemId entity = (CartitemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.cartId, entity.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, cartId);
    }

}