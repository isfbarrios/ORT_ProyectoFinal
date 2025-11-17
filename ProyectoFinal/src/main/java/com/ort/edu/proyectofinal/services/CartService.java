package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.entities.Cart;
import com.ort.edu.proyectofinal.repositories.CartItemRepository;
import com.ort.edu.proyectofinal.repositories.CartRepository;
import com.ort.edu.proyectofinal.repositories.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private SessionRepository sessionRepository;

    // Obtener o crear un cart para esta sesion
    public Cart getOrCreateCart(String sessionId) {
        return null;
    }

    // Obtener o crear un cart para esta sesion
    public Cart getOrCreateCart(UserDTO userDTO) {
        return null;
    }

    // Agrega un pedido al carrito de esa sesion
    public Cart addItemToCart(String sessionId, int menuItemId, String name, BigDecimal price, int quiantity) {
        return null;
    }
}
