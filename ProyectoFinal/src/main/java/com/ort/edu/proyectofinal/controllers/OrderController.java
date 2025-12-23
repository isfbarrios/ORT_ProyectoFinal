package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.CartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import com.ort.edu.proyectofinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderRepository repo;

    @Autowired
    private OrderService orderService;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/update_state")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateState(
            @RequestBody OrderUpdateDTO dto, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        Order updated = orderService.updateOrderState(dto);
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAll(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        List<OrderDTO> orders = repo.findAll()
                .stream()
                .map(OrderDTO::new)
                .toList();

        //TODO: Revisar
        for (Iterator<OrderDTO> it = orders.iterator(); it.hasNext();) {
            OrderDTO temp = it.next();
            if (temp.getState().getId() > 3) {
                it.remove();
            }
        }

        return ResponseEntity.ok(orders);
    }
}
