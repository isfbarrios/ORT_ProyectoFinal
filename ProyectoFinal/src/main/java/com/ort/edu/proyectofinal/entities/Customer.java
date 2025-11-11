package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "customer", schema = "proyectofinal")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CustomerId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SessionId")
    private Session session;

    @ColumnDefault("current_timestamp()")
    @Column(name = "CreatedDate", nullable = false)
    private Instant createdDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "StateId", nullable = false)
    private Customerstate state;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private Instant lastUpdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Customerstate getState() {
        return state;
    }

    public void setState(Customerstate state) {
        this.state = state;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

}