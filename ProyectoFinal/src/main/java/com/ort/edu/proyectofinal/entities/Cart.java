package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Table(name = "Cart", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TableId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Tables table;

    @JoinColumn(name = "UserName", nullable = false)
    private String userName;

    @ColumnDefault("0.00")
    @Column(name = "Amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ColumnDefault("current_timestamp()")
    @Column(name = "Date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CartStateId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cartstate cartState;

    @ColumnDefault("0")
    @Column(name = "DelayTime", nullable = false)
    private Integer delayTime;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    public Cart() {}

    public Cart(Tables table, String userName, BigDecimal amount, Cartstate cartState, Integer delayTime) {
        this.table = table;
        this.userName = userName;
        this.amount = amount;
        this.delayTime = delayTime;
        this.cartState = cartState;
        this.date = LocalDateTime.now();
        this.lastUpdate = LocalDateTime.now();
    }
}