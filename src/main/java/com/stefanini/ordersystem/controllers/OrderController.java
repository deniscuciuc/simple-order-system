package com.stefanini.ordersystem.controllers;


import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.OrderServiceImpl;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidIdException;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidStatusLogicException;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequestMapping(("/api/orders"))
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(OK)
                    .body("Order with id " + orderService.deleteOrder(id) + " deleted");
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

    @PutMapping("/start-order/{id}")
    public ResponseEntity<?> startOrder(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(OK)
                    .body(orderService.startOrder(id));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (InvalidStatusLogicException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

    @PutMapping("/finish-order/{id}")
    public ResponseEntity<?> finishOrder(@PathVariable Long id) {
        try {
            return ResponseEntity
                    .status(OK)
                    .body(orderService.finishOrder(id));
        } catch (InvalidIdException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (NotFoundException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        } catch (InvalidStatusLogicException exception) {
            return ResponseEntity
                    .status(exception.getResponseStatus())
                    .body(exception.getMessage());
        }
    }

}
