package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OrderitemId implements Serializable {
    private static final long serialVersionUID = -3242071240156899588L;
    @Column(name = "OrderId", nullable = false)
    private Integer orderId;

    @Column(name = "ItemId", nullable = false)
    private Integer itemId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
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
        OrderitemId entity = (OrderitemId) o;
        return Objects.equals(this.itemId, entity.itemId) &&
                Objects.equals(this.orderId, entity.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemId, orderId);
    }

}