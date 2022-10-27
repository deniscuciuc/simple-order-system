package com.stefanini.ordersystem.service.impl.helper;

import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.service.impl.exceptions.EnumNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class ValuesChecker {

    private static final Logger logger = LoggerFactory.getLogger(ValuesChecker.class);


    public static void verifyIfOrderTypeExists(String orderType) throws EnumNotFoundException {
        OrderType foundOrderType = Arrays.stream(OrderType.values())
                .filter(orderTypeEnum -> orderType.toUpperCase().equals(orderTypeEnum.toString()))
                .findAny()
                .orElse(null);

        if (foundOrderType == null) {
            logger.error("Order type {} not found", orderType);
            throw new EnumNotFoundException("Order type " + orderType + " not found");
        }
    }
}
