package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import jakarta.persistence.Table;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "tableavailability", schema = "proyectofinal")
public class Tableavailability {
    @EmbeddedId
    private TableavailabilityId id;

    @MapsId("tableId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "TableId", nullable = false)
    private Tables table;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "StateId", nullable = false)
    private Tablestate state;

    public TableavailabilityId getId() {
        return id;
    }

    public void setId(TableavailabilityId id) {
        this.id = id;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public Tablestate getState() {
        return state;
    }

    public void setState(Tablestate state) {
        this.state = state;
    }

}