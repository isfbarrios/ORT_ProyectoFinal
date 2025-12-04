package com.ort.edu.proyectofinal;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public final class CoreManager {

    private static CoreManager instance;

    @Value("${local.open.time.hour}")
    private int localOpen_hour;
    @Value("${local.open.time.minute}")
    private int localOpen_minute;
    @Value("${local.close.time.hour}")
    private int localClose_hour;
    @Value("${local.close.time.minute}")
    private int localClose_minute;
    @Value("${local.min.reservation.time}")
    private int local_minReserve_time;

    public static final int _AVAILABLE_STATE_ID = 1;
    public static final int _BUSY_STATE_ID = 2;
    public static final int _RESERVE_STATE_ID = 3;

    public static final String genericErrorResponse = "Error al procesar la solicitud. Intente nuevamente";

    private CoreManager() {}

    public static CoreManager getInstance() {
        if (instance == null) {
            instance = new CoreManager();
        }

        return instance;
    }
}
