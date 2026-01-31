package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Bill;
import com.ort.edu.proyectofinal.entities.Billitem;
import com.ort.edu.proyectofinal.entities.BillitemId;
import com.ort.edu.proyectofinal.entities.Cartitem;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillItemDTO {

    private BillitemId id;
    private Bill bill;
    private Cartitem cartItem;
    private Integer quantity;
    private String extraData;

    public BillItemDTO() {}

    public BillItemDTO(Billitem item) {
        this.id = item.getId();
        this.bill = item.getBill();
        this.cartItem = item.getCartItem();
        this.quantity = item.getQuantity();
        this.extraData = item.getExtraData();
    }
}
