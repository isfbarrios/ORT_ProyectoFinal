package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.MenuDTO;
import com.ort.edu.proyectofinal.dto.TableAvailabilityDTO;
import com.ort.edu.proyectofinal.repositories.TableAvailabilityRepository;
import com.ort.edu.proyectofinal.services.TableAvailabilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/table_availability")
public class TableAvailabilityController {

    @Autowired
    private TableAvailabilityRepository repo;

    @Autowired
    private TableAvailabilityService service;

    @GetMapping
    public ResponseEntity<List<TableAvailabilityDTO>> getAll() {
        List<TableAvailabilityDTO> items = service.getAvailableTables();

        return ResponseEntity.ok(items);
    }
}
