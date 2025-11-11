package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

@Embeddable
public class TableavailabilityId implements Serializable {
    private static final long serialVersionUID = -8234248950412676069L;
    @Column(name = "TableId", nullable = false)
    private Integer tableId;

    @Column(name = "ReservedTimestamp", nullable = false)
    private Instant reservedTimestamp;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Instant getReservedTimestamp() {
        return reservedTimestamp;
    }

    public void setReservedTimestamp(Instant reservedTimestamp) {
        this.reservedTimestamp = reservedTimestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TableavailabilityId entity = (TableavailabilityId) o;
        return Objects.equals(this.tableId, entity.tableId) &&
                Objects.equals(this.reservedTimestamp, entity.reservedTimestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, reservedTimestamp);
    }

}