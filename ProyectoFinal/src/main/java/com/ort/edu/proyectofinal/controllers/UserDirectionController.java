package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.UserDirectionRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import com.ort.edu.proyectofinal.services.UserDirectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user_direction")
public class UserDirectionController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDirectionService service;

    @Autowired
    private UserDirectionRepository repo;

    @Autowired
    private HttpSession session;

    private final CoreManager manager = CoreManager.getInstance();

    @GetMapping
    public ResponseEntity<?> getAll(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        UserDTO user = (UserDTO) session.getAttribute("user");

        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<UserDirectionDTO> items = repo.findByUser_Id(user.getId())
                .stream()
                .map(UserDirectionDTO::new)
                .collect(Collectors.toList());

        UserDirectionsDTO directions = new UserDirectionsDTO(user.getUsername(), items);

        return ResponseEntity.ok(directions);
    }

    @PostMapping("/new_direction")
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
