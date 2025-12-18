package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private SessionRepository repo;

    private final CoreManager manager = CoreManager.getInstance();

    public Session resolveSession(String authHeader) {
        if (authHeader == null || authHeader.isBlank()) return null;

        String sessionId = manager.getToken(authHeader);

        Optional<Session> existing = repo.findBySessionId(sessionId);

        if (existing.isPresent()) {
            return existing.get();
        }

        return createSession(manager.getUser());
    }

    public Session createSession(User user) {
        String token = manager.generateToken(jwtUtil, user);
        String sessionKey = manager.hashString(token);

        return new Session(sessionKey, LocalDateTime.now());
    }
}
