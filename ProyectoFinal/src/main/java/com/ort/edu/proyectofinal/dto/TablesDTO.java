package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Tables;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class TablesDTO {

    private Integer id;
    private String name;
    private String description;
    private Integer chairsAmount;
    private List<TableShiftDTO> shifts;

    public TablesDTO() {}

    public TablesDTO(Tables table) {
        this.id = table.getId();
        this.name = table.getName();
        this.description = table.getDescription();
        this.chairsAmount = table.getChairsAmount();
    }

    @Override
    public String toString() {
        return "TablesDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", chairsAmount=" + chairsAmount +
                ", shifts=" + shifts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TablesDTO tablesDTO = (TablesDTO) o;
        return Objects.equals(id, tablesDTO.id) && Objects.equals(name, tablesDTO.name) && Objects.equals(chairsAmount, tablesDTO.chairsAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, chairsAmount);
    }
}
