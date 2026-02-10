package com.ort.edu.proyectofinal.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ort.edu.proyectofinal.dto.UserDirectionDTO;
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

    @Override
    public String toString() {
        return "UserDirection{" +
                "id=" + id +
                ", user=" + user +
                ", streetName='" + streetName + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", comments='" + comments + '\'' +
                ", createdDate=" + createdDate +
                ", lastUpdate=" + lastUpdate +
                '}';
    }

    @Transient
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(new UserDirectionDTO(this));
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando Cartitem a JSON", e);
        }
    }
}
