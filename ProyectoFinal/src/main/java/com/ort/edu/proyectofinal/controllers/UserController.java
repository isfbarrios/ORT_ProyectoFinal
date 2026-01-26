package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.entities.Userstate;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import com.ort.edu.proyectofinal.repositories.UserstateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @Autowired
    private UserstateRepository userstateRepository;

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserDTO> getCurrentUser(Authentication authentication) {
        if (authentication == null) {
            return ResponseEntity.status(401).build();
        }
        String username = authentication.getName();
        User user = repo.findByUsername(username);
        
        if (user == null) {
             user = repo.findByMail(username);
        }
        
        if (user != null) {
             return ResponseEntity.ok(new UserDTO(user));
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getUser(@PathVariable int id) {

        Optional<User> optionalUser = repo.findById(id);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new UserDTO(optionalUser.get()));
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllUsers() {

        List<UserDTO> users = repo.findAll()
                .stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {

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

        // Asignar Userstate por defecto si no viene
        if (user.getUserstate() == null) {
            // intenta obtener estado con id=1, si no existe lo crea con nombre 'CREATED'
            Userstate defaultState = userstateRepository.findById(1).orElse(null);
            if (defaultState == null) {
                defaultState = new Userstate();
                defaultState.setName("CREATED");
                defaultState = userstateRepository.save(defaultState);
            }
            user.setUserstate(defaultState);
        }

        // Nota: Con OAuth2, la contraseña ya no es administrada localmente para usuarios OAuth.
        // Si mantenemos registro local, podemos dejar el campo password vacío o eliminarlo de la entidad si es 100% OAuth.
        
        User saved = repo.save(user);
        UserDTO dto = new UserDTO(saved);

        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@PathVariable int id,
        @RequestBody User updatedUser) {

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

        // Password update logic removed as we assume OAuth2 delegation or separate flow if hybrid

        User saved = repo.save(existing);

        return ResponseEntity.ok(new UserDTO(saved));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {

        Optional<User> optional = repo.findById(id);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        repo.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
