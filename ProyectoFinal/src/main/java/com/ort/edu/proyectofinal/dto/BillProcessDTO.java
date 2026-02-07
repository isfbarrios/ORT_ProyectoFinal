package com.ort.edu.proyectofinal.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BillProcessDTO {

    private Integer billId;
    private Integer paymentTypeId;
    private Integer cartId;
    private String deliveryMode;
    private String phone;
    private String comments;
    private Integer directionId;
    private UserDirectionDTO direction;

    @Override
    public String toString() {
        return "BillProcessDTO{" +
                "billId=" + billId +
                ", paymentTypeId=" + paymentTypeId +
                ", cartId=" + cartId +
                ", deliveryMode='" + deliveryMode + '\'' +
                ", phone='" + phone + '\'' +
                ", comments='" + comments + '\'' +
                ", directionId=" + directionId +
                '}';
    }

    @Transient
    public String toJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(this);
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializando Cartitem a JSON", e);
        }
    }
}
