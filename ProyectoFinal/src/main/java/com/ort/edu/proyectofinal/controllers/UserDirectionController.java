package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.UserDirectionRequestDTO;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.security.JwtUtil;
import com.ort.edu.proyectofinal.services.UserDirectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserDirectionController {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserDirectionService service;

    private final CoreManager manager = CoreManager.getInstance();

    @PostMapping("/direction")
    public ResponseEntity<?> saveDirection(
            @RequestBody UserDirectionRequestDTO body,
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        } catch (AuthException e) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        service.saveOrUpdate(body);

        return ResponseEntity.ok(
                new ResponseDTO("Dirección guardada correctamente")
        );
    }
}
