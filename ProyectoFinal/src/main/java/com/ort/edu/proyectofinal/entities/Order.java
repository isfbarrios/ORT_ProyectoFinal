package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "`Order`", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "CartId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Cart cart;

    @ColumnDefault("current_timestamp()")
    @Column(name = "OrderDate", nullable = false)
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "OrderStateId", nullable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Orderstate state;

    @Lob
    @Column(name = "Notes")
    private String notes;

    @ColumnDefault("current_timestamp()")
    @Column(name = "LastUpdate", nullable = false)
    private LocalDateTime lastUpdate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Orderitem> items = new ArrayList<>();

    public void addItem(Orderitem item) {
        item.setOrder(this);
        items.add(item);
    }
}
