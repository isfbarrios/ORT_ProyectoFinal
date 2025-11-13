package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Consultas automáticas via Spring Data:
    Cart findByDate(Date date);
}
