package com.ort.edu.proyectofinal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ort.edu.proyectofinal.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    // Consultas automáticas via Spring Data:
    User findByUsername(String username);
    User findByMail(String mail);
}
