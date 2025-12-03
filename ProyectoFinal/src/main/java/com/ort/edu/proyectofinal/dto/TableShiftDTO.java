package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Tableshift;
import com.ort.edu.proyectofinal.entities.TableshiftId;
import com.ort.edu.proyectofinal.entities.Tables;
import com.ort.edu.proyectofinal.entities.Tablestate;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
public class TableShiftDTO {

    private TableshiftId id;
    private String openTime;
    private String closeTime;
    private Tablestate state;

    public TableShiftDTO() {}

    public TableShiftDTO(Tableshift tableshift) {
        this.id = tableshift.getId();
        this.openTime = tableshift.getOpenTime();
        this.closeTime = tableshift.getCloseTime();
        this.state = tableshift.getState();
    }

    @Override
    public String toString() {
        return "TableShiftDTO{" +
                "id=" + id +
                ", openTime='" + openTime + '\'' +
                ", closeTime='" + closeTime + '\'' +
                ", state=" + state +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TableShiftDTO that = (TableShiftDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(openTime, that.openTime) && Objects.equals(closeTime, that.closeTime) && Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, openTime, closeTime, state);
    }
}
