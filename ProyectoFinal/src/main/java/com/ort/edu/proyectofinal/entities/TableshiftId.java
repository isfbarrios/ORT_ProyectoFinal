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
public class TableshiftId implements Serializable {

    private static final long serialVersionUID = -8234248950412676069L;

    @Column(name = "TableId", nullable = false)
    private int tableId;

    @Column(name = "ShiftId", nullable = false)
    private int shiftId;

    public TableshiftId() {}

    public TableshiftId(int tableId, int shiftId) {
        this.tableId = tableId;
        this.shiftId = shiftId;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TableshiftId that = (TableshiftId) o;
        return Objects.equals(tableId, that.tableId) && Objects.equals(shiftId, that.shiftId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableId, shiftId);
    }
}