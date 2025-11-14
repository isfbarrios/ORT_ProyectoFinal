package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.MenuDTO;
import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.repositories.MenuRepository;
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
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<MenuDTO> get(@PathVariable int id) {
        Optional<Menu> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuDTO(optional.get()));
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAll() {
        List<MenuDTO> items = repo.findAll()
                .stream()
                .map(MenuDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
