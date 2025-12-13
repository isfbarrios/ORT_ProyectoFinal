package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Orderstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderStateRepository extends JpaRepository<Orderstate, Integer> {

    // Consultas automáticas via Spring Data:
    Orderstate findByName(String name);
}
