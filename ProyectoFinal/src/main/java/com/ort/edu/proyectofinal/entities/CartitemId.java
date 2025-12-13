package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class CartitemId implements Serializable {

    private static final long serialVersionUID = -8782270964561977868L;

    @Column(name = "CartId", nullable = false)
    private Integer cartId;

    @Column(name = "ItemId", nullable = false)
    private Integer itemId;

    public CartitemId() {}

    public CartitemId(Integer cartId, Integer itemId) {
        this.cartId = cartId;
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