package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableReservationRepository;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
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

    private CoreManager manager = CoreManager.getInstance();

    @GetMapping
    public ResponseEntity<List<TablesDTO>> getAll() {
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

    @GetMapping("/table/{tableId}/reservation/{shiftId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> reserveTableShift(@PathVariable int tableId,
                                               @PathVariable int shiftId,
                                               @RequestParam LocalDate date,
                                               @RequestParam String customerName) {

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
