package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private OrderService orderService;

    @PostMapping("/update_state")
    public ResponseEntity<?> updateState(@RequestBody OrderUpdateDTO dto) {

        Order updated = orderService.updateOrderState(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<OrderDTO> orders = repo.findAll()
                .stream()
                .map(OrderDTO::new)
                .toList();

        //TODO: Revisar
        /*
        if (!orders.isEmpty()) {
            for (Iterator<OrderDTO> it = orders.iterator(); it.hasNext();) {
                OrderDTO temp = it.next();
                if (temp.getState().getId() > 3) {
                    it.remove();
                }
            }
        }
        */
        return ResponseEntity.ok(orders);
    }
}
