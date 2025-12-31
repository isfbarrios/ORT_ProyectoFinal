package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "OrderItem", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orderitem {
    @EmbeddedId
    private OrderitemId id;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "OrderId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumns({
            @JoinColumn(name = "CartId", referencedColumnName = "CartId", insertable = false, updatable = false),
            @JoinColumn(name = "ItemId", referencedColumnName = "ItemId", insertable = false, updatable = false)
    })
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cartitem cartItem;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Lob
    @Column(name = "ExtraData")
    private String extraData;
}