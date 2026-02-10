package com.ort.edu.proyectofinal.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCartItemRequestDTO {
    private int menuItemId;
    private int quantity;
    private int tableId;
}
