package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.repositories.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<CartDTO> get(@PathVariable int id) {
        Optional<Cart> optionalCart = repo.findById(id);

        if (optionalCart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartDTO(optionalCart.get()));
    }

    @GetMapping
    public ResponseEntity<List<CartDTO>> getAll() {
        List<CartDTO> carts = repo.findAll()
                .stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(carts);
    }
}
