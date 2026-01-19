package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class UserDirectionsDTO {

    private String username;
    private List<UserDirectionDTO> directions;

    public UserDirectionsDTO() {}

    public UserDirectionsDTO(String username, List<UserDirectionDTO> directions) {
        this.username = username;
        this.directions = directions;
    }

    @Override
    public String toString() {
        return "UserDirectionsDTO{" +
                "username='" + username + '\'' +
                ", directions=" + directions.toString() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserDirectionsDTO that = (UserDirectionsDTO) o;
        return Objects.equals(username, that.username) && Objects.equals(directions, that.directions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, directions);
    }
}
