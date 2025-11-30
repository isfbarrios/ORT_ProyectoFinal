package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TableavailabilityId implements Serializable {

    private static final long serialVersionUID = -8234248950412676069L;

    @Column(name = "TableId", nullable = false)
    private Integer tableId;

    @Column(name = "ReservedTimestamp", nullable = false)
    private LocalDateTime reservedTimestamp;

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