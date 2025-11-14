package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Menuitem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MenuItemRepository extends JpaRepository<Menuitem, Integer> {

    List<Menuitem> findByMenuId(int menuId);
}
