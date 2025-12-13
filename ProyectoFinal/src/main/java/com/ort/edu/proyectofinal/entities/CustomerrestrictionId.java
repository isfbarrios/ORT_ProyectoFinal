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
public class CustomerrestrictionId implements Serializable {

    private static final long serialVersionUID = -959976204629514808L;

    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "RestrictionId", nullable = false)
    private Integer restrictionId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerrestrictionId entity = (CustomerrestrictionId) o;
        return Objects.equals(this.customerId, entity.customerId) &&
                Objects.equals(this.restrictionId, entity.restrictionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, restrictionId);
    }
}