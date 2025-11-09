package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/usuarios")
    public List<User> getUsers() {
        return repo.findAll();
    }
}
