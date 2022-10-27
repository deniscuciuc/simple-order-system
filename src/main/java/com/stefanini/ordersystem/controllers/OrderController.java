package com.stefanini.ordersystem.controllers;


import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.OrderServiceImpl;
import com.stefanini.ordersystem.service.impl.exceptions.EnumNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController("/api/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderServiceImpl orderServiceImpl) {
        this.orderService = orderServiceImpl;
    }

    @PostMapping("/{order-type}")
    public ResponseEntity<?> createOrder(@PathVariable("order-type") String orderType) {
        try {
            return ResponseEntity
                    .status(CREATED)
                    .body(orderService.createOrder(orderType));
        } catch (EnumNotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

}
