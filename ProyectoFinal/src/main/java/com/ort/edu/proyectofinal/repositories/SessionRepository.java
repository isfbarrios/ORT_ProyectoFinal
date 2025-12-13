package com.ort.edu.proyectofinal.repositories;

import com.ort.edu.proyectofinal.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepository extends JpaRepository<Session, Integer> {

    //@Query("SELECT s FROM SessionEntity s WHERE s.sessionId = :sessionId")
    Optional<Session> findBySessionId(String sessionId);
}
