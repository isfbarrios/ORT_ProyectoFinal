package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.LoginRequestDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.dto.LoginResponseDTO;
import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.entities.Userstate;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import com.ort.edu.proyectofinal.repositories.UserstateRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import com.ort.edu.proyectofinal.services.SessionService;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserstateRepository userstateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SessionRepository sessionRepository;

    private final CoreManager manager = CoreManager.getInstance();

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

        // Encriptar contraseña antes de guardar
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
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

        // Actualizar contraseña solo si viene y codificarla
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

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
        return ResponseEntity.noContent().build(); // 204
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // Buscar usuario por mail
        User user = repo.findByMail(request.getMail());

        if (user == null) {
            // No existe usuario con ese mail
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO("Usuario o contraseña incorrectos"));
        }

        // Validar password con BCrypt
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO("Usuario o contraseña incorrectos"));
        }

        String token = manager.generateToken(jwtUtil, user);

        String sessionKey = UUID.randomUUID().toString();
        Session session = sessionService.resolveSession(sessionKey);

        UserDTO dto = new UserDTO(user);
        dto.setSessionId(session.getSessionId());

        manager.setUser(dto);

        LoginResponseDTO resp = new LoginResponseDTO(token, dto);

        return ResponseEntity.ok(resp);
    }
}
