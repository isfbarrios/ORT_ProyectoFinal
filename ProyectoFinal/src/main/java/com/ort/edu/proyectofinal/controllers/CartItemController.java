package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.CartItemDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.entities.Cartitem;
import com.ort.edu.proyectofinal.entities.CartitemId;
import com.ort.edu.proyectofinal.repositories.CartItemRepository;
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

    @Operation(
            summary = "Obtener item del carrito por su id",
            description = "Obtener item del carrito (menu_item) por su id"
    )
    @GetMapping("/{cartId}/{menuItemId}")
    public ResponseEntity<?> get(@PathVariable int cartId,
        @PathVariable int menuItemId) {

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
    public ResponseEntity<?> getAll() {

        List<CartItemDTO> items = repo.findAll()
                .stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<?> getAllByCart(@PathVariable int cartId) {

        List<CartItemDTO> items = repo.findByCartId(cartId)
                .stream()
                .map(CartItemDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(items);
    }
}
