package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class UserDetailsServiceImpl implements UserDetailsService {

    private UserRepository repo;

    @Autowired
    public UserDetailsServiceImpl(UserRepository repo) { this.repo = repo; }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        String authorities = user.getRoleEntities().stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(", "));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .role(authorities)
                .build();
    }
}
