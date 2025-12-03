package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tableshift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableShiftRepository extends JpaRepository<Tableshift, Integer> {

    List<Tableshift> findByTableId(int tableId);
}
