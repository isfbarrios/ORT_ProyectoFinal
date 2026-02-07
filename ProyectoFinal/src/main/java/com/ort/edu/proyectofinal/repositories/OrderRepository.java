package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByStateIdIn(List<Integer> stateIds);
}
