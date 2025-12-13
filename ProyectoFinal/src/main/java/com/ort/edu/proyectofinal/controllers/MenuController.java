package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.MenuDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.MenuRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepository repo;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> get(
            @PathVariable int id, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        Optional<Menu> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuDTO(optional.get()));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAll(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        List<MenuDTO> items = repo.findAll()
                .stream()
                .map(MenuDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
