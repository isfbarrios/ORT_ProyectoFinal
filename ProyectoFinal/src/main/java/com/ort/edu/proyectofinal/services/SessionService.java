package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repo;

    public Session resolveSession(String sessionId) {

        if (sessionId == null || sessionId.isBlank()) {

            Session s = new Session();
            s.setSessionId(UUID.randomUUID().toString());
            s.setCreatedDate(LocalDateTime.now());

            repo.save(s);

            return s;
        }

        //TODO: Revisar esto
        Optional<Session> existing = repo.findBySessionId(sessionId);

        if (existing.isPresent()) {
            return existing.get();
        }

        return null;
    }
}
