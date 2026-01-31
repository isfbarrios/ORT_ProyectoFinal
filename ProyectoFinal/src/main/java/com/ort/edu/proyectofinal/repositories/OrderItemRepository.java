package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Orderitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<Orderitem, Integer> {

    List<Orderitem> findByOrder_Id(int orderId);
}
