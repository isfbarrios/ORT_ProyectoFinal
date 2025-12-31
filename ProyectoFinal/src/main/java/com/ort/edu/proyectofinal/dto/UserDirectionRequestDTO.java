package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDirectionRequestDTO {

    private String streetName;
    private String doorNumber;
    private String phone;
    private String comments;

    // getters y setters
    /*
    {
        streetName: 'Pruebita 123',
        doorNumber: '123',
        phone: '099999999',
        comments: 'Casa con ventanas'
    }
    */
}
