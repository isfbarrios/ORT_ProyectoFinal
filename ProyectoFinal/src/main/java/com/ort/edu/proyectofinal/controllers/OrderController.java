package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/update_state")
    public ResponseEntity<Order> updateState(@RequestBody OrderUpdateDTO dto) {
        Order updated = orderService.updateOrderState(dto);
        return ResponseEntity.ok(updated);
    }
}
