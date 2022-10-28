package com.stefanini.ordersystem.service.impl;

import com.stefanini.ordersystem.dao.OrderDao;
import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.domain.enums.OrderStatus;
import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.email.EmailSender;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidStatusLogicException;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import com.stefanini.ordersystem.service.impl.helper.ValuesChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.*;


@Service
public class OrderServiceImpl implements OrderService {

    private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
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
    public Long deleteOrder(Long id) {
        ValuesChecker.verifyIfIdIsValid(id);
        findOrderByIdAndIfNotFoundThrowException(id);
        return orderDao.deleteOrderById(id);
    }

    @Override
    public Order startOrder(Long id) {
        ValuesChecker.verifyIfIdIsValid(id);
        Order order = findOrderByIdAndIfNotFoundThrowException(id);
        verifyIfOrderCanBeStartedByStatusAndIfNotThrowException(order.getStatus());

        order.setStatus(IN_PROGRESS);
        order.setInProgressFrom(Date.valueOf(LocalDate.now()));
        orderDao.updateOrder(order);

        EmailSender.sendMail(
                String.format("Order with id '%d' was started at %s. Status Updated to in progress",
                        id, order.getInProgressFrom()),
                "Order status was started to process"
        );

        return order;
    }

    @Override
    public Order finishOrder(Long id) {
        ValuesChecker.verifyIfIdIsValid(id);
        Order order = findOrderByIdAndIfNotFoundThrowException(id);
        verifyIfOrderCanBeFinishedByStatusAndIfNotThrowException(order.getStatus());

        order.setStatus(FINISHED);
        order.setFinishedAt(Date.valueOf(LocalDate.now()));
        orderDao.updateOrder(order);

        EmailSender.sendMail(
                String.format("Order with id '%d' was finished at %s. Status Updated to finished",
                        id, order.getFinishedAt()),
                "Order was finished"
        );

        return order;
    }

    private Order findOrderByIdAndIfNotFoundThrowException(Long id) throws NotFoundException {
        Order foundOrder = orderDao.findOrderById(id);
        if (foundOrder == null) {
            logger.error("Order with id {} not found", id);
            throw new NotFoundException("Order with id " + id + " not found");
        } else {
            return foundOrder;
        }
    }

    private void verifyIfOrderCanBeFinishedByStatusAndIfNotThrowException(OrderStatus status)throws InvalidStatusLogicException {
        if (status.equals(NEW)) {
            logger.error("Order can't be finished without to be in progress");
            throw new InvalidStatusLogicException("Order can't be finished without to be in progress");
        } else if (status.equals(FINISHED)) {
            logger.error("Order is already finished");
            throw new InvalidStatusLogicException("Order is already finished");
        }
    }

    private void verifyIfOrderCanBeStartedByStatusAndIfNotThrowException(OrderStatus status) {
        if (status.equals(IN_PROGRESS)) {
            logger.error("Order is already in progress");
            throw new InvalidStatusLogicException("Order is already in progress");
        } else if (status.equals(FINISHED)) {
            logger.error("Order is already finished");
            throw new InvalidStatusLogicException("Order is already finished");
        }
    }
}
