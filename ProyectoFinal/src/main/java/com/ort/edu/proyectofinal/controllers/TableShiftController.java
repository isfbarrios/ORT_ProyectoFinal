package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.*;
import com.ort.edu.proyectofinal.entities.Tables;
import com.ort.edu.proyectofinal.exception.TableException;
import com.ort.edu.proyectofinal.repositories.TableShiftRepository;
import com.ort.edu.proyectofinal.repositories.TablesRepository;
import com.ort.edu.proyectofinal.services.TableShiftService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/table_shift")
public class TableShiftController {

    @Autowired
    private TablesRepository repo;

    @Autowired
    private TableShiftRepository tableShiftRepo;

    @Autowired
    private TableShiftService service;

    @GetMapping
    public ResponseEntity<List<TablesDTO>> getAll() {
        List<TablesDTO> items = repo.findAll()
                .stream()
                .map(TablesDTO::new)
                .collect(Collectors.toList());

        items.forEach(item -> {
            List<TableShiftDTO> itemShifts = tableShiftRepo.findByTableId(item.getId())
                    .stream()
                    .map(TableShiftDTO::new)
                    .toList();

            item.setShifts(itemShifts);
        });

        return ResponseEntity.ok(items);
    }

    @GetMapping("/table/{tableId}/shift/{shiftId}")
    public ResponseEntity<ResponseDTO> reserveTableShift(@PathVariable int tableId, @PathVariable int shiftId) {

        try {
            boolean isReserved = service.reserveTableShift(tableId, shiftId);

            return ResponseEntity.ok(new ResponseDTO("Reserva realizada"));
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
