package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.BillRequestDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.BillException;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.repositories.*;
import com.ort.edu.proyectofinal.services.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody BillRequestDTO request) {
        try {
            return ResponseEntity.ok(service.create(request));
        }
        catch(CartException ce) {
            ce.printStackTrace();
            return ResponseEntity.ok(new ResponseDTO(ce.getMessage()));
        }
        catch(BillException be) {
            be.printStackTrace();
            return ResponseEntity.ok(new ResponseDTO(be.getMessage()));
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
