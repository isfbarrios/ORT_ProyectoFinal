package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.dto.AddCartItemRequestDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
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

        System.out.println();
        System.out.println(getClass().getSimpleName()+".confirm.INI");
        System.out.println();
        System.out.println("session.id: " + session.getId());
        System.out.println("session.user: " + session.getAttribute("user"));
        System.out.println();
        System.out.println(getClass().getSimpleName()+".confirm.END");
        System.out.println();

        OrderDTO order = null;

        try {
            UserDTO user = (UserDTO) session.getAttribute("user");

            if (user == null) {
                // Try to recover from SecurityContext if session is empty (should rely on success handler ideally)
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated()) {
                     // logic to re-populate session if needed, or CartService should handle it
                     // For now, assuming session has user or CartService can handle logic
                }
            }
            
            if (user == null && SecurityContextHolder.getContext().getAuthentication() == null) {
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO("Usuario no logueado"));
            }
            
            order = cartService.confirmCart(principal);
        }
        catch (CartException ce) {
            System.out.println();
            System.out.println(getClass().getSimpleName()+".confirm.exception:"+ce.getMessage());
            System.out.println();
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
    public ResponseEntity<Void> close(Principal principal) {

        cartService.closeCart(principal);
        return ResponseEntity.ok().build();
    }
}