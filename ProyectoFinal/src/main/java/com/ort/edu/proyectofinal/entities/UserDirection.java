package com.ort.edu.proyectofinal.entities;
import jakarta.persistence.*;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;

@Getter
@Setter
@Entity
@Table(name = "UserDirection")
public class UserDirection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DirectionId")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserId", nullable = false)
    private User user;

    @Column(name = "StreetName", nullable = false, length = 30)
    private String streetName;

    @Column(name = "DoorNumber", nullable = false, length = 10)
    private String doorNumber;

    @Column(name = "Phone", nullable = false, length = 15)
    private String phone;

    @Column(name = "Comments", length = 50)
    private String comments;

    @Column(name = "CreatedDate")
    private LocalDateTime createdDate;

    @Column(name = "LastUpdate")
    private LocalDateTime lastUpdate;

    // getters / setters
}
