package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

@Entity
@Table(name = "orderitem", schema = "proyectofinal")
public class Orderitem {
    @EmbeddedId
    private OrderitemId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "OrderId", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MenuItemId", nullable = false)
    private Menuitem menuItem;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Lob
    @Column(name = "ExtraData")
    private String extraData;

    public OrderitemId getId() {
        return id;
    }

    public void setId(OrderitemId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Menuitem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(Menuitem menuItem) {
        this.menuItem = menuItem;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

}