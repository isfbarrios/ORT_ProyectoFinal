package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {

    private String mail;
    private String password;
    private String userType;

    public LoginRequestDTO() {}
}
