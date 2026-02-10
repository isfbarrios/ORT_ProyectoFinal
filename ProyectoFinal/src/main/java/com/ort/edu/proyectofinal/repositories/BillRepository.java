package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {

}
