package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.MenuItemDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.repositories.MenuItemRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu_item")
public class MenuItemController {

    @Autowired
    private MenuItemRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {

        Optional<Menuitem> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuItemDTO(optional.get()));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<MenuItemDTO> items = repo.findAll()
                .stream()
                .map(MenuItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<?> getAllByCart(@PathVariable int menuId) {

        List<MenuItemDTO> items = repo.findByMenuId(menuId)
                .stream()
                .map(MenuItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
