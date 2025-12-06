package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tableshift;
import com.ort.edu.proyectofinal.entities.TableshiftId;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TableShiftRepository extends JpaRepository<Tableshift, TableshiftId> {

    List<Tableshift> findByTableId(int tableId);
}