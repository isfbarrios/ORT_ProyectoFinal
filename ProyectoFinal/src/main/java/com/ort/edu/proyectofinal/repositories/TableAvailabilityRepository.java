package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tableavailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TableAvailabilityRepository extends JpaRepository<Tableavailability, Integer> {

    @Query("SELECT t FROM Tableavailability t WHERE t.id.reservedTimestamp BETWEEN :start AND :end")
    List<Tableavailability> findAllByReservedDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
