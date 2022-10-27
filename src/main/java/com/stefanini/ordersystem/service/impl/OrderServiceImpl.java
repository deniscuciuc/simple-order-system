package com.stefanini.ordersystem.service.impl;

import com.stefanini.ordersystem.dao.OrderDao;
import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.email.EmailSender;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import com.stefanini.ordersystem.service.impl.helper.ValuesChecker;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;

    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public Order createOrder(String orderType) {
        ValuesChecker.verifyIfOrderTypeExists(orderType);

        OrderType convertedOrderType = OrderType.valueOf(orderType.toUpperCase());
        Order order = Order.createWithCreatedTimeAndEntryStatus(convertedOrderType);

        order = orderDao.createOrder(order);


        EmailSender.sendMail(
                "Here is information about new order.\n" + order.toString(),
                "New order was created"
        );

        return order;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderDao.findAllOrders();
    }

    @Override
    public Order changeOrderStatus(Long id, String status) {
        ValuesChecker.verifyIfIdIsValid(id);
        verifyIfOrderExistsById(id);
        return null;
    }

    private void verifyIfOrderExistsById(Long id) throws NotFoundException {
        // todo: check if exists by id, if not throw exception
    }
}
