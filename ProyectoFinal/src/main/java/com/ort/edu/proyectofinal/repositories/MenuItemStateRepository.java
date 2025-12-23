package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Menuitemstate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemStateRepository extends JpaRepository<Menuitemstate, Integer> {
}
