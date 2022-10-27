package com.stefanini.ordersystem.service.impl;

import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.helper.ValuesChecker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Override
    public Order createOrder(String orderType) {
        ValuesChecker.verifyIfOrderTypeExists(orderType);

        OrderType convertedOrderType = OrderType.valueOf(orderType.toUpperCase());
        Order newOrder = Order.createWithCreatedTimeAndEntryStatus(convertedOrderType);

        //TODO: email to administrator with information about order

        // TODO: save order in database
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }
}
