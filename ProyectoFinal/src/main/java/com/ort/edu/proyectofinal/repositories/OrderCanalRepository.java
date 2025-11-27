package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Ordercanal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCanalRepository extends JpaRepository<Ordercanal, Integer> {
}
