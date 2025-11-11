package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.LoginRequestDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable int id) {
        Optional<User> optionalUser = repo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new UserDTO(optionalUser.get()));
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = repo.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        // Validaciones básicas (podés mejorar con @Valid más adelante)
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        // Verificar que username y mail no existan ya
        if (repo.findByUsername(user.getUsername()) != null) {
            return ResponseEntity.status(409).build(); // CONFLICT
        }
        if (repo.findByMail(user.getMail()) != null) {
            return ResponseEntity.status(409).build();
        }

        User saved = repo.save(user);
        UserDTO dto = new UserDTO(saved);

        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        Optional<User> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existing = optional.get();
        // actualizamos campos permitidos
        existing.setName(updatedUser.getName());
        existing.setSurname(updatedUser.getSurname());
        existing.setMail(updatedUser.getMail());
        existing.setUsername(updatedUser.getUsername());
        existing.setPassword(updatedUser.getPassword());

        User saved = repo.save(existing);

        return ResponseEntity.ok(new UserDTO(saved));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        Optional<User> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {

        User user = repo.findByMail(request.getMail());

        if (user == null || !user.getPassword().equals(request.getPassword())) {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }

        return ResponseEntity.ok(new UserDTO(user));
    }

    /*
    @GetMapping("/usuarios-test")
    public User getUsers() {
        return repo.findByUsername("admin");
    }
    */
}
