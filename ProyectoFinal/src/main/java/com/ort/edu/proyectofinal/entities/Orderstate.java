package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "OrderState", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Orderstate {
    @Id
    @Column(name = "OrderStateId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 30)
    private String name;
}
