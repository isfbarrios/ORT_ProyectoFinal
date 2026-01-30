package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    // Consultas automáticas via Spring Data:
    Cart findByDate(Date date);
    Cart findByUserName(String userName);
}
