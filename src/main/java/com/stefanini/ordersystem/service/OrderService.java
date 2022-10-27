package com.stefanini.ordersystem.service;

import com.stefanini.ordersystem.domain.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(String orderType);

    List<Order> getAllOrders();

    Order changeOrderStatus(Long id, String status);
}
