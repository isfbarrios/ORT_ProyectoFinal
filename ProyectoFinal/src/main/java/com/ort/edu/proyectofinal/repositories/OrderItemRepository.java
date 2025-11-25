package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Orderitem;
import com.ort.edu.proyectofinal.entities.OrderitemId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<Orderitem, OrderitemId> {
}
