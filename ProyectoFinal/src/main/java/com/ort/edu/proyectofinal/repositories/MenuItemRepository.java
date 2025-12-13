package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Menuitem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<Menuitem, Integer> {

    List<Menuitem> findByMenuId(int menuId);
}
