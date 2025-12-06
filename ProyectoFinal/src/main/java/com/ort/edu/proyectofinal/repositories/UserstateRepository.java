package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Userstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserstateRepository extends JpaRepository<Userstate, Integer> {
}
