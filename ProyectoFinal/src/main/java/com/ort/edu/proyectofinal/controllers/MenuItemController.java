package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.MenuItemDTO;
import com.ort.edu.proyectofinal.entities.Menuitem;
import com.ort.edu.proyectofinal.repositories.MenuItemRepository;
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
@RequestMapping("/api/menu_item")
public class MenuItemController {

    @Autowired
    private MenuItemRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<MenuItemDTO> get(@PathVariable int id) {
        Optional<Menuitem> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuItemDTO(optional.get()));
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDTO>> getAll() {
        List<MenuItemDTO> items = repo.findAll()
                .stream()
                .map(MenuItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @GetMapping("/menu/{menuId}")
    public ResponseEntity<List<MenuItemDTO>> getAllByCart(@PathVariable int menuId) {
        List<MenuItemDTO> items = repo.findByMenuId(menuId)
                .stream()
                .map(MenuItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
