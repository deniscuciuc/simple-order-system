package com.stefanini.ordersystem.dao;

import com.stefanini.ordersystem.domain.Order;

import java.util.List;

public interface OrderDao {

    Order createOrder(Order order);

    List<Order> findAllOrders();

    Long deleteOrderById(Long id);

    Order findOrderById(Long id);

    Order updateOrder(Order order);
}
