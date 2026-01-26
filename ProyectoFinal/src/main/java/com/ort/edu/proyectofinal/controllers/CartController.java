package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.repositories.CartRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartRepository repo;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> get(@PathVariable int id) {

        Optional<Cart> optionalCart = repo.findById(id);

        if (optionalCart.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartDTO(optionalCart.get()));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAll() {

        List<CartDTO> carts = repo.findAll()
                .stream()
                .map(CartDTO::new)
                .collect(Collectors.toList());

        //TODO: Revisar
        for (Iterator<CartDTO> it = carts.iterator(); it.hasNext();) {
            CartDTO temp = it.next();
            if (temp.getCartState().getId() != 2) {
                it.remove();
            }
        }

        return ResponseEntity.ok(carts);
    }
}
