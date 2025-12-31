package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.CartItemDTO;
import com.ort.edu.proyectofinal.dto.OrderItemDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.OrderItemRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/order_item")
public class OrderItemController {


    @Autowired
    private JwtUtil jwtUtil;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private OrderItemRepository repo;

    @GetMapping("/order/{orderId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllByOrderId(
            @PathVariable int cartId, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        List<OrderItemDTO> items = repo.findByCartItem_Id_CartId(cartId)
                .stream()
                .map(OrderItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
