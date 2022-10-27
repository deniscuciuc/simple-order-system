package com.stefanini.ordersystem.service.impl.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@ResponseStatus(NOT_FOUND)
public class EnumNotFoundException extends RuntimeException {

    public EnumNotFoundException(String message) {
        super(message);
    }

    public HttpStatus getResponseStatus() {
        return NOT_FOUND;
    }
}
