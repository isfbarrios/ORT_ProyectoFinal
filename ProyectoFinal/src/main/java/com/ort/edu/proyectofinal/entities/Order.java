package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "`order`", schema = "proyectofinal")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId", nullable = false)
    private Integer id;

    @Column(name = "OrderNumber", nullable = false, length = 30)
    private String orderNumber;

    @Column(name = "Amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ColumnDefault("current_timestamp()")
    @Column(name = "Date", nullable = false)
    private Instant date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "StateId", nullable = false)
    private Orderstate state;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CanalId", nullable = false)
    private Ordercanal canal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentTypeId")
    private Paymenttype paymentType;

    @Lob
    @Column(name = "Description")
    private String description;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;

    @Lob
    @Column(name = "ExtraData")
    private String extraData;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
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

    public Orderstate getState() {
        return state;
    }

    public void setState(Orderstate state) {
        this.state = state;
    }

    public Ordercanal getCanal() {
        return canal;
    }

    public void setCanal(Ordercanal canal) {
        this.canal = canal;
    }

    public Paymenttype getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Paymenttype paymentType) {
        this.paymentType = paymentType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }

}