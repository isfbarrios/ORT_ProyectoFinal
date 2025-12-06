package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.LoginRequestDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.dto.LoginResponseDTO;
import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.entities.Userstate;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import com.ort.edu.proyectofinal.services.SessionService;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
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
    private SessionService sessionService;

    @Autowired
    private com.ort.edu.proyectofinal.repositories.UserstateRepository userstateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

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
        // Actualizar contraseña solo si viene y codificarla
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

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

        // Armar DTO para no devolver la entidad cruda
        UserDTO dto = new UserDTO(user);

        Session session = sessionService.resolveSession("");
        if (session != null) dto.setSessionId(session.getSessionId());

        // Generar token JWT con el username como subject
        String token = jwtUtil.generateToken(user.getUsername());

        LoginResponseDTO resp = new LoginResponseDTO(token, dto);
        return ResponseEntity.ok(resp);
    }

    /*
    @GetMapping("/usuarios-test")
    public User getUsers() {
        return repo.findByUsername("admin");
    }
    */
}
