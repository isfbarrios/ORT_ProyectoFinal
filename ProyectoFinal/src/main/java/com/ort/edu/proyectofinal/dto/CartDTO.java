package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartstate;
import com.ort.edu.proyectofinal.entities.Tables;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class CartDTO {

    private Integer id;
    private Tables table;
    private BigDecimal amount;
    private LocalDateTime date;
    private Cartstate cartState;
    private Integer delayTime;

    public CartDTO() {}

    public CartDTO(Cart cart) {
        this.id = cart.getId();
        this.table = cart.getTable();
        this.amount = cart.getAmount();
        this.date = cart.getDate();
        this.cartState = cart.getCartState();
        this.delayTime = cart.getDelayTime();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CartDTO cartDTO = (CartDTO) o;
        return Objects.equals(id, cartDTO.id) && Objects.equals(date, cartDTO.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date);
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "id=" + id +
                ", table=" + table +
                ", amount=" + amount +
                ", date=" + date +
                ", cartState=" + cartState +
                ", delayTime=" + delayTime +
                '}';
    }
}
