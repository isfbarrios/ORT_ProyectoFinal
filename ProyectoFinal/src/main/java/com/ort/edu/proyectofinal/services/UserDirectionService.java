package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.dto.UserDirectionRequestDTO;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.entities.UserDirection;
import com.ort.edu.proyectofinal.repositories.UserDirectionRepository;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
@Service
public class UserDirectionService {

    @Autowired
    private UserDirectionRepository repo;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

    private final CoreManager manager = CoreManager.getInstance();

    public void saveOrUpdate(UserDirectionRequestDTO dto) {

        UserDTO userDTO = (UserDTO) session.getAttribute("user");
        if (userDTO == null) {
            throw new RuntimeException("Usuario no logueado");
        }

        User user = userRepository.findByUsername(userDTO.getUsername());

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
}
