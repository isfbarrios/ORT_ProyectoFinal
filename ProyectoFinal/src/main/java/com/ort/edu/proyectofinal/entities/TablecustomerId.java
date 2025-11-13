package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class TablecustomerId implements Serializable {
    private static final long serialVersionUID = 7468013144321986675L;
    @Column(name = "TableId", nullable = false)
    private Integer tableId;

    @Column(name = "CustomerId", nullable = false)
    private Integer customerId;

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TablecustomerId entity = (TablecustomerId) o;
        return Objects.equals(this.customerId, entity.customerId) &&
                Objects.equals(this.tableId, entity.tableId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, tableId);
    }

}