package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Integer> {


}
