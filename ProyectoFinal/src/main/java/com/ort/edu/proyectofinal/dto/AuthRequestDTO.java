package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.CoreManager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthRequestDTO {

    private String nombreUsuario;
    private String contrasenia;
    private String userType;

    @Override
    public String toString() {
        return "AuthRequestDTO{" +
                "nombreUsuario='" + nombreUsuario + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", userType='" + userType + '\'' +
                '}';
    }
}
