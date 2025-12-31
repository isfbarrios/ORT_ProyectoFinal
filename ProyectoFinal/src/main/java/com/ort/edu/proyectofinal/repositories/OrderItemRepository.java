package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Orderitem;
import com.ort.edu.proyectofinal.entities.OrderitemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<Orderitem, OrderitemId> {

    List<Orderitem> findByCartItem_Id_CartId(int cartId);
}
