package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.TableReservationRequestDTO;
import com.ort.edu.proyectofinal.entities.TableReservation;
import com.ort.edu.proyectofinal.entities.Tableshift;
import com.ort.edu.proyectofinal.entities.TableshiftId;
import com.ort.edu.proyectofinal.entities.Tablestate;
import com.ort.edu.proyectofinal.entities.Tables;
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

    // Configuración desde application.properties
    @Value("${local.tables.total}")
    private int localTablesTotal;

    @Value("${local.tables.reserve.limit-percentage}")
    private double reserveLimitPercentage;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void reserve(TableReservationRequestDTO request) throws TableException {

        int tableId = request.getTableId();
        int shiftId = request.getShiftId();
        LocalDate date = request.getReservationDate();
        String customerName = request.getCustomerName();

        // Validar nombre de cliente
        if (customerName == null || customerName.trim().isEmpty()) {
            throw new TableException("Debe indicar el nombre del cliente para la reserva.");
        }

        // 1) Validar que la mesa exista
        tablesRepo.findById(tableId)
                .orElseThrow(() -> new TableException("La mesa indicada no existe."));

        // 2) Validar que el turno exista para esa mesa
        TableshiftId tableshiftId = new TableshiftId(tableId, shiftId);
        Tableshift shift = tableShiftRepo.findById(tableshiftId)
                .orElseThrow(() -> new TableException("El turno indicado no existe para esa mesa."));

        // 3) Validar que el turno esté disponible
        if (shift.getState() == null ||
                shift.getState().getId() == null ||
                !shift.getState().getId().equals(CoreManager._AVAILABLE_STATE_ID)) {

            throw new TableException("El turno seleccionado no está disponible para reservar.");
        }

        // 4) Validar configuración
        if (localTablesTotal <= 0) {
            throw new TableException("La cantidad total de mesas del local no está configurada correctamente.");
        }

        long maxMesasReservables = Math.max(1, (long) Math.floor(localTablesTotal * reserveLimitPercentage));

        // 5) Regla 1: una reserva por mesa y fecha
        long reservasDeEsaMesaEseDia = tableReservationRepo.countByTableIdAndReservationDate(tableId, date);

        if (reservasDeEsaMesaEseDia >= 1) {
            throw new TableException("La mesa ya tiene una reserva para esa fecha.");
        }

        // 6) Regla 2: solo el X% de las mesas del local puede estar reservado
        long mesasReservadasEseDia = tableReservationRepo.countDistinctTablesByReservationDate(date);

        if (mesasReservadasEseDia >= maxMesasReservables) {
            throw new TableException(
                    "Ya se alcanzó el máximo de mesas reservadas para esa fecha. Límite: "
                            + maxMesasReservables + " de " + localTablesTotal + " mesas.");
        }

        // 7) Crear la reserva
        TableReservation nueva = new TableReservation();
            nueva.setTableId(tableId);
            nueva.setShiftId(shiftId);
            nueva.setCustomerName(customerName.trim());
            nueva.setReservationDate(date);
            nueva.setCreatedDate(LocalDateTime.now());
            nueva.setLastUpdate(LocalDateTime.now());

        try {
            tableReservationRepo.saveAndFlush(nueva);
        } catch (DataIntegrityViolationException ex) {
            // Refuerza la regla en caso de condición de carrera
            throw new TableException("La mesa ya tiene una reserva para esa fecha (validación en base de datos).");
        }

        // 8) Cambiar el turno a "reservado"
        Tablestate reservedState = tablestateRepo.findById(CoreManager._RESERVE_STATE_ID)
                .orElseThrow(() -> new TableException("No se encontró el estado reservado."));

        shift.setState(reservedState);
        tableShiftRepo.save(shift);
    }
}
