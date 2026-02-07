package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.UserDirectionRequestDTO;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.entities.UserDirection;
import com.ort.edu.proyectofinal.repositories.UserDirectionRepository;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;

@Service
public class UserDirectionService {

    @Autowired
    private UserDirectionRepository repo;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void save(UserDirectionRequestDTO dto, Principal principal) {

        String userName = principal.getName();

        User user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        UserDirection direction = new UserDirection();

        direction.setUser(user);
        direction.setCreatedDate(LocalDateTime.now());

        direction.setStreetName(dto.getStreetName());
        direction.setDoorNumber(dto.getDoorNumber());
        direction.setPhone(dto.getPhone());
        direction.setComments(dto.getComments());
        direction.setLastUpdate(LocalDateTime.now());

        repo.save(direction);
    }

    @Transactional
    public void delete(Integer id, Principal principal) {
        String userName = principal.getName();
        User user = userRepository.findByUsername(userName);

        if (user == null) {
            throw new RuntimeException("Usuario no encontrado");
        }

        UserDirection direction = repo.findByIdAndUser_Id(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Dirección no encontrada"));

        repo.delete(direction);
    }
}
