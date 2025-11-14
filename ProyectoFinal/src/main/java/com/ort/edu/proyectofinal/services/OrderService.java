package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.entities.Order;
import com.ort.edu.proyectofinal.entities.Orderstate;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderStateRepository stateRepo;

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
