package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.entities.Session;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository repo;

    @Autowired
    private HttpSession httpSession;

    public Session resolveSession(String sessionId) {

        if (sessionId == null || sessionId.isBlank()) {
            return createSession();
        }

        //TODO: Revisar esto
        Optional<Session> existing = repo.findBySessionId(sessionId);

        if (existing.isPresent()) {
            return existing.get();
        }
        else {
            return createSession();
        }
    }

    public Session createSession() {
        Session s = new Session(httpSession.getId(), LocalDateTime.now());

        repo.saveAndFlush(s);

        return s;
    }
}
