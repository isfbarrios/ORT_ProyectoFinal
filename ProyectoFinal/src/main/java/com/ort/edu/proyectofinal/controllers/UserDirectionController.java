package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.repositories.UserDirectionRepository;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import com.ort.edu.proyectofinal.services.UserDirectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getAll(Principal principal) {

        String userName = principal.getName();

        User user = userRepository.findByUsername(userName);

        if (user == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        List<UserDirectionDTO> items = repo.findByUser_Id(user.getId())
                .stream()
                .map(UserDirectionDTO::new)
                .collect(Collectors.toList());

        UserDirectionsDTO directions = new UserDirectionsDTO(user.getUsername(), items);

        System.out.println("UserDirectionController.getAll all");
        System.out.println(directions.toString());

        return ResponseEntity.ok(directions);
    }

    @PostMapping("/new_direction")
    public ResponseEntity<?> save(@RequestBody UserDirectionRequestDTO body, Principal principal) {

        service.save(body, principal);

        return ResponseEntity.ok(
                new ResponseDTO("Dirección guardada correctamente")
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id, Principal principal) {

        service.deleteDirection(id, principal);

        return ResponseEntity.ok(
                new ResponseDTO("Dirección eliminada correctamente")
        );
    }
}
