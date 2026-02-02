package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.AddCartItemRequestDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ort.edu.proyectofinal.repositories.UserRepository;

import java.security.Principal;

@RestController
@RequestMapping("/api/session_cart")
public class SessionCartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity<?> getCart(Principal principal) {

        SessionCartDTO cart = cartService.getOrCreateCart(principal);

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/items")
    public ResponseEntity<?> addItem(@RequestBody AddCartItemRequestDTO body, Principal principal) {

        try {
            SessionCartDTO cart = cartService.addItemToCart(principal, body.getMenuItemId(), body.getQuantity());

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
    public ResponseEntity<?> confirm(Principal principal) throws OrderException {

        OrderDTO order = null;

        try {
            order = cartService.confirmCart(principal);
        }
        catch (CartException ce) {
            System.out.println(getClass().getSimpleName()+".confirm.exception:"+ce.getMessage());
            throw new OrderException("No pudimos confirmar tu orden");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(order);
    }
}