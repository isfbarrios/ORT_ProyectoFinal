package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;

@Entity
@Table(name = "tablecustomer", schema = "proyectofinal")
public class Tablecustomer {
    @EmbeddedId
    private TablecustomerId id;

    @MapsId("tableId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TableId", nullable = false)
    private Tables table;

    @MapsId("customerId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CustomerId", nullable = false)
    private Customer customer;

    @ColumnDefault("current_timestamp()")
    @Column(name = "CreatedDate", nullable = false)
    private Instant createdDate;

    public TablecustomerId getId() {
        return id;
    }

    public void setId(TablecustomerId id) {
        this.id = id;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}