package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<Cartitem, CartitemId>  {

    // Consultas automáticas via Spring Data:
    List<Cartitem> findByCartId(int cartId);
    List<Cartitem> findByCartIdAndProcessed(int cartId, int processed);
}
