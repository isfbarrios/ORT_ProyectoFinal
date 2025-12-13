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
public class CustomerpreferenceId implements Serializable {
    private static final long serialVersionUID = -8348616915448749594L;
    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    @Column(name = "PreferenceId", nullable = false)
    private Integer preferenceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CustomerpreferenceId entity = (CustomerpreferenceId) o;
        return Objects.equals(this.preferenceId, entity.preferenceId) &&
                Objects.equals(this.customerId, entity.customerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preferenceId, customerId);
    }
}