package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableReservationRepository;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ort.edu.proyectofinal.services.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/table_reservation")
public class TableReservationController {

    @Autowired
    private TablesRepository repo;

    @Autowired
    private TableShiftRepository tableShiftRepo;

    @Autowired
    private TableReservationRepository tableReservationRepo;

    @Autowired
    private TableReservationService service;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping
    public ResponseEntity<?> getAll(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        List<TablesDTO> items = repo.findAll()
                .stream()
                .map(TablesDTO::new)
                .collect(Collectors.toList());

        items.forEach(item -> {
            List<TableReservationDTO> itemShifts = tableShiftRepo.findByTableId(item.getId())
                    .stream()
                    .map(com.ort.edu.proyectofinal.dto.TableReservationDTO::new)
                    .toList();

            item.setShifts(itemShifts);
        });

        return ResponseEntity.ok(items);
    }

    @PostMapping("/reserve")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> reserveTableShift(
        @RequestBody int tableId, @RequestBody int shiftId, @RequestBody LocalDate date,
        @RequestBody String customerName, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        try {
            boolean isReserved = service.reserve(tableId, shiftId, date, customerName);

            return isReserved ? ResponseEntity.ok(new ResponseDTO("Reserva realizada"))
                    : ResponseEntity.ok(new ResponseDTO(CoreManager.genericErrorResponse));
        }
        catch (TableException te) {
            te.printStackTrace();
            return ResponseEntity.ok(new ResponseDTO(te.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(new ResponseDTO(CoreManager.genericErrorResponse));
        }
    }
}
