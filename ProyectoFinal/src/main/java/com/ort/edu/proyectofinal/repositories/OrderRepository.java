package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {


}
