package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.UserDirection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class UserDirectionDTO {

    private Integer id;
    private String streetName;
    private String doorNumber;
    private String phone;
    private String comments;
    private LocalDateTime createdDate;

    public UserDirectionDTO() {}

    public UserDirectionDTO(UserDirection userDirection) {
        this.id = userDirection.getId();
        this.streetName = userDirection.getStreetName();
        this.doorNumber = userDirection.getDoorNumber();
        this.phone = userDirection.getPhone();
        this.comments = userDirection.getComments();
        this.createdDate = userDirection.getCreatedDate();
    }

    @Override
    public String toString() {
        return "UserDirectionDTO{" +
                "id=" + id +
                ", streetName='" + streetName + '\'' +
                ", doorNumber='" + doorNumber + '\'' +
                ", phone='" + phone + '\'' +
                ", comments='" + comments + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDirectionDTO that = (UserDirectionDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(streetName, that.streetName) && Objects.equals(doorNumber, that.doorNumber) && Objects.equals(phone, that.phone) && Objects.equals(comments, that.comments) && Objects.equals(createdDate, that.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, streetName, doorNumber, phone, comments, createdDate);
    }
}
