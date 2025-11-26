package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.Cartstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartStateRepository extends JpaRepository<Cartstate, Integer> {

    // Consultas automáticas via Spring Data:
    Cartstate findByName(String name);
}
