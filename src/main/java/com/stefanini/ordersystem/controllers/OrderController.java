package com.stefanini.ordersystem.controllers;


import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.OrderServiceImpl;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidIdException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping(("/api/order"))
@RestController
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
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @PutMapping("/{id}/{status}")
    public ResponseEntity<?> changeOrderStatus(@PathVariable Long id, @PathVariable String status) {
        try {
            return ResponseEntity
                    .status(OK)
                    .body(orderService.changeOrderStatus(id, status));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }

    }

}
