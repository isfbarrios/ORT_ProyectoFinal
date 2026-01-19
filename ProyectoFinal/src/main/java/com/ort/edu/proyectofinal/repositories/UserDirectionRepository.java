package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.UserDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDirectionRepository
        extends JpaRepository<UserDirection, Integer> {

    // Obtener la dirección del usuario logueado
    List<UserDirection> findByUser_Id(Integer userId);
}
