package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<Cartitem, CartitemId>  {

    // Consultas automáticas via Spring Data:
    List<Cartitem> findByCartId(int cartId);

    //List<Cartitem> findByCartIdAndProcessed(int cartId, int processed);

    @Query("SELECT ci FROM Cartitem ci WHERE ci.id.cartId = :cartId AND ci.processed = :processed")
    List<Cartitem> findByCartIdAndProcessed(@Param("cartId") Integer cartId, @Param("processed") Integer processed);
}
