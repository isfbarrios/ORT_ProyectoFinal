package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tablestate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableStateRepository extends JpaRepository<Tablestate, Integer>  {

}
