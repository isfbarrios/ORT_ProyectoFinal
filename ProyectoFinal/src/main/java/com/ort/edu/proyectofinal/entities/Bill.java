package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "Bill", schema = "proyectofinal")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BillId", nullable = false)
    private Integer id;

    @Column(name = "BillNumber", nullable = false, length = 30)
    private String billNumber;

    @Column(name = "Amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @ColumnDefault("current_timestamp()")
    @Column(name = "Date", nullable = false)
    private LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PaymentTypeId")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paymenttype paymentType;

    @Lob
    @Column(name = "ExtraData")
    private String extraData;
}
