package com.ort.edu.proyectofinal.controllers;

import com.ort.edu.proyectofinal.CoreManager;
import com.ort.edu.proyectofinal.dto.AddCartItemRequestDTO;
import com.ort.edu.proyectofinal.dto.ResponseDTO;
import com.ort.edu.proyectofinal.dto.SessionCartDTO;
import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.exception.AuthException;
import com.ort.edu.proyectofinal.exception.CartException;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.security.JwtUtil;
import com.ort.edu.proyectofinal.services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import com.ort.edu.proyectofinal.repositories.UserRepository;

@RestController
@RequestMapping("/api/session_cart")
public class SessionCartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private HttpSession session;

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

        SessionCartDTO cart = cartService.getOrCreateCart();

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
            SessionCartDTO cart = cartService.addItemToCart(body.getMenuItemId(), body.getQuantity());

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

        System.out.println();
        System.out.println(getClass().getSimpleName()+".confirm.INI");
        System.out.println();
        System.out.println("authHeader: " + authHeader);
        System.out.println("session.id: " + session.getId());
        System.out.println("session.user: " + session.getAttribute("user"));
        System.out.println();
        System.out.println(getClass().getSimpleName()+".confirm.END");
        System.out.println();

        OrderDTO order = null;

        try {
            UserDTO user = (UserDTO) session.getAttribute("user");

            if (user == null) {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();

                if (auth != null && auth.isAuthenticated() && !auth.getPrincipal().equals("anonymousUser")) {

                    String username = null;

                    if (auth.getPrincipal() instanceof UserDetails) {
                        username = ((UserDetails) auth.getPrincipal()).getUsername();
                    }
                    else if (auth.getPrincipal() instanceof String) {
                        username = (String) auth.getPrincipal();
                    }

                    if (username != null) {
                        com.ort.edu.proyectofinal.entities.User dbUser = userRepository.findByUsername(username);
                        if (dbUser != null) {
                            user = new UserDTO(dbUser);
                            
                            // Recuperado por JWT. Confiamos en el ID de sesión de la base.
                            session.setAttribute("user", user);
                        }
                    }
                }
            }
            
            if (user == null) {
                 return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ResponseDTO("Usuario no logueado"));
            }
            order = cartService.confirmCart();
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
    public ResponseEntity<Void> close(@RequestHeader(value = "Authorization", required = false) String authHeader) {

        cartService.closeCart(authHeader);
        return ResponseEntity.ok().build();
    }
}