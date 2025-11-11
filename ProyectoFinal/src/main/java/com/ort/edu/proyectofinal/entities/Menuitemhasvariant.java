package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "menuitemhasvariant", schema = "proyectofinal")
public class Menuitemhasvariant {
    @EmbeddedId
    private MenuitemhasvariantId id;

    @MapsId("menuItemId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "MenuItemId", nullable = false)
    private Menuitem menuItem;

    @MapsId("variantId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "VariantId", nullable = false)
    private Menuitemvariant variant;

    @ColumnDefault("0.00")
    @Column(name = "PriceDelta", nullable = false, precision = 12, scale = 2)
    private BigDecimal priceDelta;

    public MenuitemhasvariantId getId() {
        return id;
    }

    public void setId(MenuitemhasvariantId id) {
        this.id = id;
    }

    public Menuitem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menuitem menuItem) {
        this.menuItem = menuItem;
    }

    public Menuitemvariant getVariant() {
        return variant;
    }

    public void setVariant(Menuitemvariant variant) {
        this.variant = variant;
    }

    public BigDecimal getPriceDelta() {
        return priceDelta;
    }

    public void setPriceDelta(BigDecimal priceDelta) {
        this.priceDelta = priceDelta;
    }

}