package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.AddCartItemRequestDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.security.JwtUtil;
import com.ort.edu.proyectofinal.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session_cart")
public class SessionCartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private JwtUtil jwtUtil;

    private final CoreManager manager = CoreManager.getInstance();

    @GetMapping
    public ResponseEntity<?> getCart(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        System.out.println(getClass().getSimpleName() + ".getCart.token: " + manager.getToken(authHeader));

        SessionCartDTO cart = cartService.getOrCreateCart(manager.getToken(authHeader));

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody AddCartItemRequestDTO body,
                                     @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        try {
            SessionCartDTO cart = cartService.addItemToCart(
                    authHeader,
                    body.getMenuItemId(),
                    body.getQuantity()
            );

            return ResponseEntity.ok(cart);
        }
        catch (CartException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return null;
    }


    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(
            @RequestHeader(value = "Authorization", required = false) String authHeader) throws OrderException {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        OrderDTO order = null;

        try {
            order = cartService.confirmCart(authHeader);
        }
        catch (CartException ce) {
            throw new OrderException("No pudimos confirmar tu orden");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (order == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(order);
    }

    @PostMapping("/close")
    public ResponseEntity<Void> close(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        cartService.closeCart(authHeader);
        return ResponseEntity.ok().build();
    }
}