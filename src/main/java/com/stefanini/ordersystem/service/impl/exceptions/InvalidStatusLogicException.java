package com.stefanini.ordersystem.service.impl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.BAD_GATEWAY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ResponseStatus(BAD_GATEWAY)
public class InvalidStatusLogicException extends RuntimeException {

    public InvalidStatusLogicException(String message) {
        super(message);
    }

    public HttpStatus getResponseStatus() {
        return BAD_REQUEST;
    }
}
