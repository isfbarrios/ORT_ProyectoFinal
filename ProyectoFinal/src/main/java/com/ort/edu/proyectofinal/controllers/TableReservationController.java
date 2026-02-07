package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
import com.ort.edu.proyectofinal.services.TableReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    private TableReservationService service;

    @GetMapping
    public ResponseEntity<?> getAll() {

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
    public ResponseEntity<?> reserveTableShift(@RequestBody ReservationRequestDTO request) {

        try {
            boolean isReserved = service.reserve(request.getTableId(), request.getShiftId(), request.getDate(), request.getCustomerName());

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
