package com.stefanini.ordersystem.controllers;

import com.stefanini.ordersystem.domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static com.stefanini.ordersystem.domain.enums.OrderStatus.*;
import static com.stefanini.ordersystem.domain.enums.OrderType.REPAIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderControllerTest extends AbstractTest {

    private final String BASIC_PATH = "/api/order";

    protected OrderControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    // todo: use h2 database

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
    }

    @Test
    void shouldReturnCreatedResponseStatusAndNewCreatedOrderInBody() throws Exception {
        String url = BASIC_PATH + "/repair";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(url)).andReturn();
        int status = mvcResult.getResponse().getStatus();
        assertThat(status).isEqualTo(201);
    }

    @Test
    void getAllOrders() {

    }

    @Test
    void deleteOrder() {
    }

    @Test
    void startOrder() {
    }

    @Test
    void finishOrder() {
    }
}