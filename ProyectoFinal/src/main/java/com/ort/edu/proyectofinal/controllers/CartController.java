package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {

        Optional<Cart> optionalCart = repo.findById(id);

        if (optionalCart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartDTO(optionalCart.get()));
    }
}
