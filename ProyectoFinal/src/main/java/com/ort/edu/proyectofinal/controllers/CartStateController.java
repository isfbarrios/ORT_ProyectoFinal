package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.CartStateDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Cartstate;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.CartStateRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart_state")
public class CartStateController {

    @Autowired
    private CartStateRepository repo;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> get(@PathVariable int id, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        Optional<Cartstate> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartStateDTO(optional.get()));
    }
}
