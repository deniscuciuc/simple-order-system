package com.stefanini.ordersystem.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class OrderControllerTest extends AbstractTest {

    private final String BASIC_PATH = "/api/order";

    protected OrderControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

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
    @Disabled
    void getAllOrders() {

    }

    @Test
    @Disabled
    void deleteOrder() {
    }

    @Test
    @Disabled
    void startOrder() {
    }

    @Test
    @Disabled
    void finishOrder() {
    }
}