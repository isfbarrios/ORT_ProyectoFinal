package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Orderstate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface OrderStateRepository extends JpaRepository<Orderstate, Integer> {

    // Consultas automáticas via Spring Data:
    Orderstate findByName(String name);
}
