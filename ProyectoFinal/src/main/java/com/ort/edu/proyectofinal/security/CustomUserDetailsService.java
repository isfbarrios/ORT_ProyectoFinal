package com.ort.edu.proyectofinal.security;

import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        
        if (user == null) {
            // Also try by email just in case
            user = userRepository.findByMail(username);
        }

        if (user == null) {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList() // Roles can be added here if needed
        );
    }
}
