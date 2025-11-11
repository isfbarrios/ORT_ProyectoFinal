package com.ort.edu.proyectofinal.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "products", schema = "proyectofinal")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ProductId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 150)
    private String name;

    @Column(name = "ExpirationDate")
    private LocalDate expirationDate;

    @ColumnDefault("0.000")
    @Column(name = "Stock", nullable = false, precision = 12, scale = 3)
    private BigDecimal stock;

    @Column(name = "IncomeDate")
    private LocalDate incomeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryId")
    private Productcategory category;

    @ColumnDefault("current_timestamp()")
    @Column(name = "CreatedDate", nullable = false)
    private Instant createdDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public BigDecimal getStock() {
        return stock;
    }

    public void setStock(BigDecimal stock) {
        this.stock = stock;
    }

    public LocalDate getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(LocalDate incomeDate) {
        this.incomeDate = incomeDate;
    }

    public Productcategory getCategory() {
        return category;
    }

    public void setCategory(Productcategory category) {
        this.category = category;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

}