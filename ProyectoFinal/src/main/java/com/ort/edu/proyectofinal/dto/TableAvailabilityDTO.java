package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Tableavailability;
import com.ort.edu.proyectofinal.entities.TableavailabilityId;
import com.ort.edu.proyectofinal.entities.Tables;
import com.ort.edu.proyectofinal.entities.Tablestate;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class TableAvailabilityDTO {

    private TableavailabilityId id;
    private Tables table;
    private Tablestate state;

    public TableAvailabilityDTO() {}

    public TableAvailabilityDTO(Tableavailability tAvailability) {
        this.id = tAvailability.getId();
        this.table = tAvailability.getTable();
        this.state = tAvailability.getState();
    }

    @Override
    public String toString() {
        return "TableAvailabilityDTO{" +
                "id=" + id +
                ", table=" + table +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TableAvailabilityDTO that = (TableAvailabilityDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(table, that.table) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, table, state);
    }
}
