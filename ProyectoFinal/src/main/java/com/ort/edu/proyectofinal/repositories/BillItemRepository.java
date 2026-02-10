package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Billitem;
import com.ort.edu.proyectofinal.entities.BillitemId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillItemRepository extends JpaRepository<Billitem, BillitemId> {

}
