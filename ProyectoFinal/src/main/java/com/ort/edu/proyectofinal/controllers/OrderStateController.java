package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.dto.OrderStateDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Orderstate;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order_state")
public class OrderStateController {

    @Autowired
    private OrderStateRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {

        Optional<Orderstate> optionalOrderState = repo.findById(id);

        if (optionalOrderState.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new OrderStateDTO(optionalOrderState.get()));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<OrderStateDTO> states = repo.findAll()
                .stream()
                .map(OrderStateDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(states);
    }
}
