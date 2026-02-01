package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.dto.UserDTO;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.exception.OrderException;
import com.ort.edu.proyectofinal.repositories.CartItemRepository;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import com.ort.edu.proyectofinal.repositories.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderStateRepository stateRepo;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartItemRepository cartItemRepository;

    public Order createOrder(Cart cart, List<Cartitem> items) throws OrderException {

        if (items == null || items.isEmpty()) {
            throw new OrderException("La orden debe contener al menos un ítem");
        }

        Order order = new Order();
        order.setOrderDate(LocalDateTime.now());
        order.setLastUpdate(LocalDateTime.now());
        order.setCart(cart);

        // Estado inicial (por ejemplo, id = 1 "Pendiente")
        Orderstate initialState = stateRepo.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado inicial de la orden no encontrado"));
        order.setState(initialState);

        for (Cartitem cartItem : items) {
            Orderitem item = new Orderitem();
            item.setOrder(order);
            item.setMenuItem(cartItem.getMenuItem());
            item.setQuantity(cartItem.getQuantity());
            item.setUnitPrice(cartItem.getItemAmount());
            item.setNotes(null);

            order.addItem(item);

            cartItem.setProcessed(1);

            cartItemRepository.saveAndFlush(cartItem);
        }

        System.out.println();
        System.out.println("confirmCart.order: " + order.toString());
        System.out.println();

        // Hibernate genera order.id y luego los order_item.order_id
        return orderRepo.saveAndFlush(order);
    }

    public Order updateOrderState(OrderUpdateDTO dto) {

        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Orderstate state = stateRepo.findById(dto.getOrderStateId())
                .orElseThrow(() -> new RuntimeException("State not found"));

        order.setState(state);
        order.setLastUpdate(LocalDateTime.now());

        return orderRepo.save(order);
    }
}
