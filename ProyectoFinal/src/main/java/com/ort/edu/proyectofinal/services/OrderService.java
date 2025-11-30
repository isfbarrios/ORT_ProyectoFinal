package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import com.ort.edu.proyectofinal.repositories.OrderCanalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderStateRepository stateRepo;

    @Autowired
    private OrderCanalRepository canalRepo;

    //TODO: Para crear las ordenes, deberiamos hacerlo por aca. Ver bien que es lo que deberiamos recibir por params
    public Order createOrder(List<Cartitem> items) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("La orden debe contener al menos un ítem");
        }

        Order order = new Order();
        order.setDate(Instant.now());
        order.setLastUpdate(Instant.now());

        // Número de orden simple (podés cambiar la estrategia)
        order.setOrderNumber(generateOrderNumber());

        // Estado inicial (por ejemplo, id = 1 "Pendiente")
        Orderstate initialState = stateRepo.findById(1)
                .orElseThrow(() -> new RuntimeException("Estado inicial de la orden no encontrado"));
        order.setState(initialState);

        // Canal por defecto (por ejemplo, id = 1 "Salón")
        Ordercanal defaultCanal = canalRepo.findById(1)
                .orElseThrow(() -> new RuntimeException("Canal por defecto no encontrado"));
        order.setCanal(defaultCanal);

        // Podés setear descripción si querés guardar algo del carrito/mesa
        order.setDescription(null);

        // Calcular total y crear Orderitems
        BigDecimal total = BigDecimal.ZERO;

        for (Cartitem cartItem : items) {

            if (cartItem.getItemAmount() != null) {
                total = total.add(cartItem.getItemAmount());
            }

            Orderitem item = new Orderitem();
            OrderitemId itemId = new OrderitemId();
            // El orderId lo setea Hibernate cuando se persista la orden
            itemId.setItemId(cartItem.getMenuItem().getId());

            item.setId(itemId);
            item.setMenuItem(cartItem.getMenuItem());
            item.setQuantity(cartItem.getQuantity());
            item.setExtraData(null);

            order.addItem(item);
        }

        order.setAmount(total);

        // Hibernate genera order.id y luego los order_item.order_id
        return orderRepo.save(order);
    }

    private String generateOrderNumber() {
        // Ejemplo simple: ORD-<timestamp>
        return "ORD-" + System.currentTimeMillis();
    }

    public Order updateOrderState(OrderUpdateDTO dto) {

        Order order = orderRepo.findById(dto.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Orderstate state = stateRepo.findById(dto.getOrderStateId())
                .orElseThrow(() -> new RuntimeException("State not found"));

        order.setState(state);
        order.setLastUpdate(java.time.Instant.now());

        return orderRepo.save(order);
    }
}

