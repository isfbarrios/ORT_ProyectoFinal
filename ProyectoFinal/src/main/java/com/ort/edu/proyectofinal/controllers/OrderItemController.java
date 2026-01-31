package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.OrderItemDTO;
import com.ort.edu.proyectofinal.repositories.OrderItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order_item")
public class OrderItemController {

    @Autowired
    private OrderItemRepository repo;


    @GetMapping("/order/{orderId}")
    public ResponseEntity<?> getAllByOrderId(
            @PathVariable int orderId) {

        List<OrderItemDTO> items = repo.findByOrder_Id(orderId)
                .stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
