package com.ort.edu.proyectofinal.services;

import com.ort.edu.proyectofinal.dto.OrderDTO;
import com.ort.edu.proyectofinal.dto.OrderUpdateDTO;
import com.ort.edu.proyectofinal.entities.*;
import com.ort.edu.proyectofinal.repositories.OrderRepository;
import com.ort.edu.proyectofinal.repositories.OrderStateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepo;

    @Autowired
    private OrderStateRepository stateRepo;

    //TODO: Para crear las ordenes, deberiamos hacerlo por aca. Ver bien que es lo que deberiamos recibir por params
    public Order createOrder(List<Cartitem> items) {

        Order order = new Order();
        order.setDate(Instant.now());

        for (Cartitem temp : items) {

            Orderitem item = new Orderitem();

            OrderitemId itemId = new OrderitemId();

            item.setId(itemId);
            item.setQuantity(temp.getQuantity());

            order.addItem(item);
        }

        // acá Hibernate genera: order.id y luego los order_item.order_id
        return orderRepo.save(order);
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
