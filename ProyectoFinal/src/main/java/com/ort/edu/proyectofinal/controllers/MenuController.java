package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.MenuDTO;
import com.ort.edu.proyectofinal.entities.Menu;
import com.ort.edu.proyectofinal.repositories.MenuRepository;
import com.ort.edu.proyectofinal.services.MenuImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/menu")
public class MenuController {

    @Autowired
    private MenuRepository repo;

    @Autowired
    private MenuImportService menuImportService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable int id, Principal principal) {

        Optional<Menu> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new MenuDTO(optional.get()));
    }

    @GetMapping
    public ResponseEntity<?> getAll() {

        List<MenuDTO> items = repo.findAll()
                .stream()
                .map(MenuDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @PostMapping("/import")
    public ResponseEntity<?> uploadExcel(@RequestParam("file") MultipartFile file) {

        try {
            menuImportService.importExcel(file);
            return ResponseEntity.ok("Menú importado correctamente.");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
