package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TablesRepository extends JpaRepository<Tables, Integer> {

    Tables findByTableId(Integer tableId);
    Tables findByStateId(Integer stateId);
    //TODO: Hacer la consulta por fecha
}
