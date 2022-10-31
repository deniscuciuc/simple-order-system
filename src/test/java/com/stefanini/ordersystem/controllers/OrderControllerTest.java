package com.stefanini.ordersystem.controllers;

import com.stefanini.ordersystem.jdbc.JdbcConnection;
import com.stefanini.ordersystem.jdbc.JdbcConnectionImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.sql.SQLException;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.*;
import static com.stefanini.ordersystem.domain.enums.OrderType.REPAIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderControllerTest extends AbstractTest {

    @Mock
    private JdbcConnection jdbcConnection;
    private final String BASIC_PATH = "/api/order";

    protected OrderControllerTest(WebApplicationContext webApplicationContext) {
        super(webApplicationContext);
    }

    @Override
    @BeforeEach
    protected void setUp() {
        super.setUp();
        try {
            given(jdbcConnection.getConnection()).willReturn(getTestConnection());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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