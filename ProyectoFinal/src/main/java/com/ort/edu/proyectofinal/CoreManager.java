package com.ort.edu.proyectofinal;

import lombok.Getter;

@Getter
public final class CoreManager {

    private static CoreManager instance;

    // Estados de mesa / turno
    public static final int _AVAILABLE_STATE_ID = 1;
    public static final int _BUSY_STATE_ID = 2;
    public static final int _RESERVE_STATE_ID = 3;

    public static final double RESERVE_LIMIT = 0.40;

    public static final String genericErrorResponse = "Error al procesar la solicitud. Intente nuevamente";

    private CoreManager() {}

    public static CoreManager getInstance() {
        if (instance == null) {
            instance = new CoreManager();
        }
        return instance;
    }
}
