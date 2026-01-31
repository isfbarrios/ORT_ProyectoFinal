package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "OrderItem", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orderitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderItemId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrderId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ProductId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "UnitPrice", nullable = false, precision = 12, scale = 2)
    private java.math.BigDecimal unitPrice;

    @Lob
    @Column(name = "Notes")
    private String notes;
}
