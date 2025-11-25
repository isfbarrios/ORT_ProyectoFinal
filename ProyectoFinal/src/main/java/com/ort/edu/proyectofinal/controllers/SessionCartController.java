package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.services.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/session-cart")
@CrossOrigin(origins = "*")
public class SessionCartController {

    private final CartService cartService;

    public SessionCartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    public ResponseEntity<SessionCartDTO> getCart(
            @RequestHeader(name = "X-Session-Id", required = false) String sessionId) {

        SessionCartDTO cart = cartService.getOrCreateCart(sessionId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<SessionCartDTO> addItem(
            @RequestHeader(name = "X-Session-Id", required = false) String sessionId,
            @RequestBody AddCartItemRequest body) {

        SessionCartDTO cart = cartService.addItemToCart(
                sessionId,
                body.getMenuItemId(),
                body.getQuantity()
        );
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/confirm")
    public ResponseEntity<OrderDTO> confirm(
            @RequestHeader(name = "X-Session-Id") String sessionId) {

        OrderDTO order = cartService.confirmCart(sessionId);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/close")
    public ResponseEntity<Void> close(
            @RequestHeader(name = "X-Session-Id") String sessionId) {

        cartService.closeCart(sessionId);
        return ResponseEntity.ok().build();
    }

    // ====== DTO interno para el body de /items ======

    public static class AddCartItemRequest {
        private int menuItemId;
        private int quantity;

        public int getMenuItemId() {
            return menuItemId;
        }

        public void setMenuItemId(int menuItemId) {
            this.menuItemId = menuItemId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }
}
