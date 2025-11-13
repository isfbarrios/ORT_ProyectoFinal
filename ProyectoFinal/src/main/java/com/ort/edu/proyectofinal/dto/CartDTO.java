package com.ort.edu.proyectofinal.dto;

import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartstate;
import com.ort.edu.proyectofinal.entities.Tables;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public class CartDTO {

    private Integer id;
    private Tables table;
    private BigDecimal amount;
    private Instant date;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Tables getTable() {
        return table;
    }

    public void setTable(Tables table) {
        this.table = table;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Cartstate getCartState() {
        return cartState;
    }

    public void setCartState(Cartstate cartState) {
        this.cartState = cartState;
    }

    public Integer getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(Integer delayTime) {
        this.delayTime = delayTime;
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
