package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Order;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ort.edu.proyectofinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    private CoreManager manager = CoreManager.getInstance();

    @PostMapping("/update_state")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateState(@RequestBody OrderUpdateDTO dto) {
        Order updated = orderService.updateOrderState(dto);
        return ResponseEntity.ok(updated);
    }
}
