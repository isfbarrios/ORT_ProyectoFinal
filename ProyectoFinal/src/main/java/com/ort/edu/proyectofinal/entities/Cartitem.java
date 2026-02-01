package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import jakarta.persistence.Table;

import java.math.BigDecimal;

@Setter
@Getter
@Entity
@Table(name = "CartItem", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Cartitem {
    @EmbeddedId
    private CartitemId id;

    @MapsId("cartId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "CartId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cart cart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MenuItemId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Menuitem menuItem;



    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "ItemAmount", nullable = false, precision = 12, scale = 2)
    private BigDecimal itemAmount;

    @ColumnDefault("0")
    @Column(name = "DelayTime", nullable = false)
    private Integer delayTime;

    @ColumnDefault("0")
    @Column(name = "Processed", nullable = false)
    private Integer processed;

    @Transient
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando Cartitem a JSON", e);
        }
    }
}