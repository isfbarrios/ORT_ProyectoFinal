package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cartstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartStateRepository extends JpaRepository<Cartstate, Integer> {

    // Consultas automáticas via Spring Data:
    Cartstate findByName(String name);
}
