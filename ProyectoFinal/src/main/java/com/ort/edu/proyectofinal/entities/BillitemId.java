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
public class BillitemId implements Serializable {
    private static final long serialVersionUID = 6698315400934195772L;

    @Column(name = "BillId", nullable = false)
    private Integer billId;

    @Column(name = "ItemId", nullable = false)
    private Integer itemId;

    @Column(name = "CartId", nullable = false)
    private Integer cartId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BillitemId entity = (BillitemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.billId, entity.billId) &&
                Objects.equals(this.cartId, entity.cartId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, billId);
    }
}
