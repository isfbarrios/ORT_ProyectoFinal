package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Menuitemstate;
import com.ort.edu.proyectofinal.entities.Menuitemtype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuItemTypeRepository extends JpaRepository<Menuitemtype, Integer> {
}
