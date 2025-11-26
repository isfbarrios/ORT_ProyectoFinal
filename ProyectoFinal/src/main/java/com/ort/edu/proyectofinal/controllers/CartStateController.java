package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.dto.CartStateDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.Cartstate;
import com.ort.edu.proyectofinal.repositories.CartStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart_state")
public class CartStateController {

    @Autowired
    private CartStateRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<CartStateDTO> get(@PathVariable int id) {
        Optional<Cartstate> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartStateDTO(optional.get()));
    }
}
