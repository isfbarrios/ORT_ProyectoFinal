package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.CartItemDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.repositories.CartItemRepository;
import com.ort.edu.proyectofinal.security.JwtUtil;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart_items")
@Tag(name = "Cart Items", description = "Endpoints para manejar items dentro del carrito")
public class CartItemController {

    @Autowired
    private CartItemRepository repo;

    private final CoreManager manager = CoreManager.getInstance();

    @Autowired
    private JwtUtil jwtUtil;

    @Operation(
            summary = "Obtener item del carrito por su id",
            description = "Obtener item del carrito (menu_item) por su id"
    )
    @GetMapping("/{cartId}/{menuItemId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> get(@PathVariable int cartId,
        @PathVariable int menuItemId, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        CartitemId cartitemId = new CartitemId();
        cartitemId.setCartId(cartId);
        cartitemId.setItemId(menuItemId);

        Optional<Cartitem> optional = repo.findById(cartitemId);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(new CartItemDTO(optional.get()));
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

        List<CartItemDTO> items = repo.findAll()
                .stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @GetMapping("/cart/{cartId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getAllByCart(
            @PathVariable int cartId, @RequestHeader(value = "Authorization", required = false) String authHeader) {

        // Validar token JWT
        try {
            manager.validateTokenJWT(jwtUtil, authHeader);
        }
        catch (AuthException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ResponseDTO(e.getMessage()));
        }

        List<CartItemDTO> items = repo.findByCartId(cartId)
                .stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
