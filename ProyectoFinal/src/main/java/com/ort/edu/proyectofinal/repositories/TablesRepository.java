package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Integer> {

    //Tables findByStateId(Integer stateId);
    //TODO: Hacer la consulta por fecha
}
