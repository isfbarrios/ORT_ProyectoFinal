package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cart", schema = "proyectofinal")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CartId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TableId")
    private Tables table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "SessionId", nullable = false)
    private Session session;

    @ColumnDefault("0.00")
    @Column(name = "Amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ColumnDefault("current_timestamp()")
    @Column(name = "Date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CartStateId", nullable = false)
    private Cartstate cartState;

    @ColumnDefault("0")
    @Column(name = "DelayTime", nullable = false)
    private Integer delayTime;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Cartstate getCartState() {
        return cartState;
    }

    public void setCartState(Cartstate cartState) {
        this.cartState = cartState;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}