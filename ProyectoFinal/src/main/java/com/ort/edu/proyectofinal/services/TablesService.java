package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.repositories.TablesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TablesService {

    @Autowired
    private TablesRepository tablesRepository;


}
