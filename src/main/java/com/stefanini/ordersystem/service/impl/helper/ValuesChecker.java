package com.stefanini.ordersystem.service.impl.helper;

import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidIdException;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ValuesChecker {

    private static final Logger logger = LoggerFactory.getLogger(ValuesChecker.class);


    public static void verifyIfOrderTypeExists(String orderType) throws NotFoundException {
        OrderType foundOrderType = Arrays.stream(OrderType.values())
                .filter(orderTypeEnum -> orderType.toUpperCase().equals(orderTypeEnum.toString()))
                .findAny()
                .orElse(null);

        if (foundOrderType == null) {
            logger.error("Order type {} not found", orderType);
            throw new NotFoundException("Order type '" + orderType + "' not found");
        }
    }

    public static void verifyIfIdIsValid(Long id) {
        if (id == 0) {
            logger.error("Id can't be zero");
            throw new InvalidIdException("Id can't be zero");
        } else if (id < 0) {
            logger.error("Id can't be negative");
            throw new InvalidIdException("Id can't be negative");
        }
    }
}
