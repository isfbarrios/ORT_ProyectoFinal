package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartItemDTO;
import com.ort.edu.proyectofinal.dto.ErrorResponseDTO;
import com.ort.edu.proyectofinal.dto.TableShiftDTO;
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
@RequestMapping("/api/table_availability")
public class TableShiftController {

    @Autowired
    private TablesRepository repo;

    @Autowired
    private TableShiftService service;

    @GetMapping
    public ResponseEntity<List<TableShiftDTO>> getAll() {

        List<Tables> items = repo.findAll();
        //TODO: Pasar a TablesDTO
        return ResponseEntity.ok(items);
    }

    @GetMapping("/table/{tableId}/shift/{shiftId}")
    public ResponseEntity<List<CartItemDTO>> reserveTableShift(@PathVariable int tableId, @PathVariable int shiftId) {

        try {
            service.reserveTableShift(tableId, shiftId);
            return ResponseEntity.ok(items);
        }
        catch (TableException te) {
            te.printStackTrace();
            //return ResponseEntity.ok(new ErrorResponseDTO(te.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok(items);
        }
    }
}
