package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Setter
@Getter
@Entity
@Table(name = "cart", schema = "proyectofinal")
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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SessionId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Session session;

    @ColumnDefault("0.00")
    @Column(name = "Amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ColumnDefault("current_timestamp()")
    @Column(name = "Date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CartStateId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cartstate cartState;

    @ColumnDefault("0")
    @Column(name = "DelayTime", nullable = false)
    private Integer delayTime;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;

    public Cart() {}

    public Cart(Tables table, Session session, BigDecimal amount, Cartstate cartState, Integer delayTime) {
        this.table = table;
        this.session = session;
        this.amount = amount;
        this.delayTime = delayTime;
        this.cartState = cartState;
        this.date = Instant.now();
        this.lastUpdate = Instant.now();
    }
}