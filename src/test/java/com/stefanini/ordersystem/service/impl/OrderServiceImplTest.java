package com.stefanini.ordersystem.service.impl;

import com.stefanini.ordersystem.dao.impl.OrderDaoImpl;
import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.service.OrderService;
import com.stefanini.ordersystem.service.impl.exceptions.InvalidStatusLogicException;
import com.stefanini.ordersystem.service.impl.exceptions.NotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.*;
import static com.stefanini.ordersystem.domain.enums.OrderType.REPAIR;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

class OrderServiceImplTest {

    @Mock
    private OrderDaoImpl orderDao;
    private OrderService underTest;
    private AutoCloseable autoCloseable;
    private ArgumentCaptor<Order> orderArgumentCaptor;
    private ArgumentCaptor<Long> longArgumentCaptor;


    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new OrderServiceImpl(orderDao);
        orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
        longArgumentCaptor = ArgumentCaptor.forClass(Long.class);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    /**
     * Unit test for {@link OrderService#createOrder(String) createOrder} method
     */
    @Test
    void shouldCreateOrderWithStatusOfNewOrder() {
        String orderType = "repair";

        underTest.createOrder(orderType);

        verify(orderDao).createOrder(orderArgumentCaptor.capture());

        Order capturedOrder = orderArgumentCaptor.getValue();

        assertThat(capturedOrder.getStatus()).isEqualTo(NEW);
    }

    /**
     * Unit test for {@link OrderService#getAllOrders() getAllOrders} method
     */
    @Test
    void shouldReturnAllOrders() {
        underTest.getAllOrders();
        verify(orderDao).findAllOrders();
    }

    /**
     * Unit test for {@link OrderService#deleteOrder(Long) deleteOrder} method
     */
    @Test
    void shouldThrowNotFoundExceptionIfUserWithSuchIdNotExists() {
        Long fakeId = (long) 25;

        given(orderDao.findOrderById(fakeId)).willReturn(null);

        assertThrows(NotFoundException.class, () -> underTest.deleteOrder(fakeId));
    }

    /**
     * Unit test for {@link OrderService#deleteOrder(Long) deleteOrder} method
     */
    @Test
    void shouldDeleteOrderIfExists() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 25);

        given(orderDao.findOrderById(order.getId())).willReturn(order);

        underTest.deleteOrder(order.getId());

        verify(orderDao).deleteOrderById(longArgumentCaptor.capture());
        Long capturedId = longArgumentCaptor.getValue();
        assertThat(capturedId).isEqualTo(order.getId());
    }

    /**
     * Unit test for {@link OrderService#startOrder(Long) startOrder} method
     */
    @Test
    void shouldThrowInvalidStatusLogicExceptionIfOrderStatusIsAlreadyInProgress() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 2);
        order.setStatus(IN_PROGRESS);

        given(orderDao.findOrderById(order.getId())).willReturn(order);
        assertThrows(InvalidStatusLogicException.class, () -> underTest.startOrder(order.getId()));
    }

    /**
     * Unit test for {@link OrderService#startOrder(Long) startOrder} method
     */
    @Test
    void shouldThrowInvalidStatusLogicExceptionIfOrderStatusIsAlreadyFinished() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 2);
        order.setStatus(FINISHED);

        given(orderDao.findOrderById(order.getId())).willReturn(order);
        assertThrows(InvalidStatusLogicException.class, () -> underTest.startOrder(order.getId()));
    }


    /**
     * Unit test for {@link OrderService#startOrder(Long) startOrder} method
     */
    @Test
    void shouldUpdateStatusOfOrderToInProgress() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 24);

        given(orderDao.findOrderById(order.getId())).willReturn(order);

        underTest.startOrder(order.getId());

        verify(orderDao).updateOrder(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();
        assertThat(capturedOrder.getStatus()).isEqualTo(IN_PROGRESS);
    }

    /**
     * Unit test for {@link OrderService#finishOrder(Long) finishOrder} method
     */
    @Test
    void shouldThrowInvalidStatusLogicExceptionIfOrderIsNew() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 2);

        given(orderDao.findOrderById(order.getId())).willReturn(order);
        assertThrows(InvalidStatusLogicException.class, () -> underTest.finishOrder(order.getId()));
    }

    /**
     * Unit test for {@link OrderService#finishOrder(Long) finishOrder} method
     */
    @Test
    void shouldThrowInvalidStatusLogicExceptionIfOrderIsAlreadyFinished() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 2);
        order.setStatus(FINISHED);

        given(orderDao.findOrderById(order.getId())).willReturn(order);
        assertThrows(InvalidStatusLogicException.class, () -> underTest.finishOrder(order.getId()));
    }

    /**
     * Unit test for {@link OrderService#finishOrder(Long) finishOrder} method
     */
    @Test
    void shouldChangeOrderStatusToFinished() {
        Order order = Order.createWithCreatedTimeAndEntryStatus(REPAIR);
        order.setId((long) 2);
        order.setStatus(IN_PROGRESS);

        given(orderDao.findOrderById(order.getId())).willReturn(order);

        underTest.finishOrder(order.getId());

        verify(orderDao).updateOrder(orderArgumentCaptor.capture());
        Order capturedOrder = orderArgumentCaptor.getValue();
        assertThat(capturedOrder.getStatus()).isEqualTo(FINISHED);
    }
}