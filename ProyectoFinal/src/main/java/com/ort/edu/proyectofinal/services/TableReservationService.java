package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.entities.Tablereservation;
import com.ort.edu.proyectofinal.entities.Tableshift;
import com.ort.edu.proyectofinal.entities.TableshiftId;
import com.ort.edu.proyectofinal.entities.Tablestate;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableReservationRepository;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TableStateRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class TableReservationService {

    @Autowired
    private TableReservationRepository tableReservationRepo;

    @Autowired
    private TableShiftRepository tableShiftRepo;

    @Autowired
    private TablesRepository tablesRepo;

    @Autowired
    private TableStateRepository tablestateRepo;

    @Value("${local.tables.total}")
    private int localTablesTotal;

    @Value("${local.tables.reserve.limit-percentage}")
    private double reserveLimitPercentage;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public boolean reserve(int tableId, int shiftId, LocalDate date, String customerName) throws TableException {

        if (customerName == null || customerName.isBlank()) {
            throw new TableException("Debe indicar el nombre del cliente para la reserva");
        }

        tablesRepo.findById(tableId).orElseThrow(() -> new TableException("La mesa indicada no existe"));

        TableshiftId tableshiftId = new TableshiftId(tableId, shiftId);

        Tableshift shift = tableShiftRepo.findById(tableshiftId)
                .orElseThrow(() -> new TableException("El turno indicado no existe para esa mesa"));

        int reservationLimit = Math.max(1, (int)Math.floor(localTablesTotal * reserveLimitPercentage));

        // Reglas
        int tableReservationForShiftAndDate = tableReservationRepo
                .countByTableIdAndShiftIdAndReservationDate(tableId, shiftId, date);

        if (tableReservationForShiftAndDate >= 1) {
            throw new TableException("La mesa ya tiene una reserva para ese turno y fecha");
        }

        int tableReservationsToday = tableReservationRepo.countDistinctTablesByReservationDate(date);

        if (tableReservationsToday >= reservationLimit) {
            throw new TableException(
                    "Ya se alcanzó el máximo de mesas reservadas para esa fecha. Límite: "
                            + reservationLimit + " de " + localTablesTotal + " mesas");
        }

        //Crear la reserva
        Tablereservation nueva = new Tablereservation();
            nueva.setTableId(tableId);
            nueva.setShiftId(shiftId);
            nueva.setCustomerName(customerName);
            nueva.setReservationDate(date);
            nueva.setCreatedDate(LocalDateTime.now());
            nueva.setLastUpdate(LocalDateTime.now());

        try {
            tableReservationRepo.saveAndFlush(nueva);
        }
        catch (DataIntegrityViolationException ex) {
            throw new TableException("La mesa ya tiene una reserva para esa fecha (validación en base de datos)");
        }

        return true;
    }
}
