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
public class OrderinprogressId implements Serializable {
    private static final long serialVersionUID = 1800169105774616881L;
    @Column(name = "OrderId", nullable = false)
    private Integer orderId;

    @Column(name = "StepId", nullable = false)
    private Integer stepId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStepId() {
        return stepId;
    }

    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        OrderinprogressId entity = (OrderinprogressId) o;
        return Objects.equals(this.orderId, entity.orderId) &&
                Objects.equals(this.stepId, entity.stepId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, stepId);
    }
}