package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.jwt.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {

        // Validación de usuario REAL:
        if (!"fabribarrios@hotmail.com".equals(username) || !"pass1234".equals(password)) {
            return ResponseEntity.status(401).body(Map.of("error", "Usuario o contraseña incorrectos"));
        }

        String token = jwtService.generateToken(username);

        return ResponseEntity.ok().body("{\"token\":\"" + token + "\"}");
    }
}
