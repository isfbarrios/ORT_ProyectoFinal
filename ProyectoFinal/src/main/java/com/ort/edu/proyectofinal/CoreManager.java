package com.ort.edu.proyectofinal;

import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.security.JwtUtil;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
public final class CoreManager {

    private static CoreManager instance;

    // Estados de mesa / turno
    public static final int _AVAILABLE_STATE_ID = 1;
    public static final int _BUSY_STATE_ID = 2;
    public static final int _RESERVE_STATE_ID = 3;

    
    public static final double RESERVE_LIMIT = 0.40;

    public static final String genericErrorResponse = "Error al procesar la solicitud. Intente nuevamente";

    private CoreManager() {
    }

    public static CoreManager getInstance() {
        if (instance == null) {
            instance = new CoreManager();
        }
        return instance;
    }

    // Validar token JWT
    public void validateTokenJWT(JwtUtil jwtUtil, String authHeader) throws AuthException {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthException("Token no proporcionado o formato inválido");
        }

        // Extrae el token sin "Bearer "
        String token = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);
            if (!jwtUtil.isTokenValid(token, username)) {
                throw new AuthException("Token inválido o expirado");
            }
        } catch (Exception ex) {
            throw new AuthException("Token inválido o error al validar");
        }
    }
}
