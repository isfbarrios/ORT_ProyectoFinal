package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.UserDirection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDirectionRepository
        extends JpaRepository<UserDirection, Integer> {

    // Obtener la dirección del usuario logueado
    List<UserDirection> findByUser_Id(Integer userId);
    Optional<UserDirection> findByIdAndUser_Id(Integer id, Integer userId);
}
