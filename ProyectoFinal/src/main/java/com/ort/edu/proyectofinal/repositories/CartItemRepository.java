package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cartitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<Cartitem, Integer>  {

    // Consultas automáticas via Spring Data:
    List<Cartitem> findByCartId(int cartId);
}
