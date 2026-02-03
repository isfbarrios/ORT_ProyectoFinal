package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Paymenttype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<Paymenttype, Integer> {
}
