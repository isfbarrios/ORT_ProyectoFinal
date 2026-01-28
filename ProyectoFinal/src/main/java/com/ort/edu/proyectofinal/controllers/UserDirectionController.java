package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.repositories.UserDirectionRepository;
import com.ort.edu.proyectofinal.services.UserDirectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user_direction")
public class UserDirectionController {

    @Autowired
    private UserDirectionService service;

    @Autowired
    private UserDirectionRepository repo;

    @Autowired
    private HttpSession session;

    @GetMapping
    public ResponseEntity<?> getAll() {

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
            @RequestBody UserDirectionRequestDTO body) {

        service.saveOrUpdate(body);

        return ResponseEntity.ok(
                new ResponseDTO("Dirección guardada correctamente")
        );
    }
}
