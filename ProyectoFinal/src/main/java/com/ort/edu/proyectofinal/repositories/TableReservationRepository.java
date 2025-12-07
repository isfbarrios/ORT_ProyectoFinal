package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Tablereservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface TableReservationRepository extends JpaRepository<Tablereservation, Integer> {

    int countByTableIdAndReservationDate(int tableId, LocalDate reservationDate);

    @Query("""
             SELECT COUNT(DISTINCT r.tableId)
             FROM Tablereservation r
             WHERE r.reservationDate = :date
            """)
    int countDistinctTablesByReservationDate(@Param("date") LocalDate date);
}
