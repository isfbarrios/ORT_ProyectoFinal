package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.AddCartItemRequestDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.entities.User;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.ort.edu.proyectofinal.repositories.UserRepository;

import java.security.Principal;

@RestController
@RequestMapping("/api/session_cart")
public class SessionCartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpSession session;

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

    @PostMapping("/close")
    public ResponseEntity<Void> close(Principal principal) {

        cartService.closeCart(principal);
        return ResponseEntity.ok().build();
    }
}