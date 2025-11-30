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
public class MenuitemhasvariantId implements Serializable {

    private static final long serialVersionUID = 5723731278691599715L;

    @Column(name = "MenuItemId", nullable = false)
    private Integer menuItemId;

    @Column(name = "VariantId", nullable = false)
    private Integer variantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        MenuitemhasvariantId entity = (MenuitemhasvariantId) o;
        return Objects.equals(this.variantId, entity.variantId) &&
                Objects.equals(this.menuItemId, entity.menuItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(variantId, menuItemId);
    }
}