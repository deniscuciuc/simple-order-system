package com.stefanini.ordersystem.dao.impl;

import com.stefanini.ordersystem.dao.OrderDao;
import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.domain.enums.OrderStatus;
import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.jdbc.JdbcFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final String ORDER_TABLE_NAME = "Orders";
    private final String ID_ROW_NAME = "id";
    private final String TYPE_ROW_NAME = "type";
    private final String STATUS_ROW_NAME = "status";
    private final String CREATED_AT_ROW_NAME = "created_at";
    private final String IN_PROGRESS_FROM_ROW_NAME = "in_progress_from";
    private final String FINISHED_AT_ROW_NAME = "finished_at";

    @Override
    public Order createOrder(Order order) {
        String insertQuery = String.format("INSERT INTO %s (%s, %s, %s, %s, %s) VALUES ('%s', '%s', '%s', %s, %s)",
                ORDER_TABLE_NAME, TYPE_ROW_NAME, STATUS_ROW_NAME, CREATED_AT_ROW_NAME,
                IN_PROGRESS_FROM_ROW_NAME, FINISHED_AT_ROW_NAME, order.getType(), order.getStatus(),
                order.getCreatedAt(), order.getInProgressFrom(), order.getFinishedAt());

        String getLastAddedOrderQuery = String.format("SELECT * FROM %s WHERE %s = (SELECT LAST_INSERT_ID())",
                ORDER_TABLE_NAME, ID_ROW_NAME);

        try (Connection connection = JdbcFactory.getInstance().getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(insertQuery);
            ResultSet resultSet = statement.executeQuery(getLastAddedOrderQuery);

            Order afterInsertingOrder = new Order();
            while (resultSet.next()) {
                afterInsertingOrder.setId(resultSet.getLong(ID_ROW_NAME));
                afterInsertingOrder.setType(OrderType.valueOf(resultSet.getString(TYPE_ROW_NAME)));
                afterInsertingOrder.setStatus(OrderStatus.valueOf(resultSet.getString(STATUS_ROW_NAME)));
                afterInsertingOrder.setCreatedAt(resultSet.getDate(CREATED_AT_ROW_NAME));
                afterInsertingOrder.setInProgressFrom(resultSet.getDate(IN_PROGRESS_FROM_ROW_NAME));
                afterInsertingOrder.setFinishedAt(resultSet.getDate(FINISHED_AT_ROW_NAME));
            }

            return afterInsertingOrder;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Order> findAllOrders() {
        String query = "SELECT * FROM " + ORDER_TABLE_NAME;

        try (Connection connection = JdbcFactory.getInstance().getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            List<Order> foundedOrder = new ArrayList<>();

            while (resultSet.next()) {
                foundedOrder.add(new Order(
                        resultSet.getLong(ID_ROW_NAME),
                        OrderType.valueOf(resultSet.getString(TYPE_ROW_NAME)),
                        OrderStatus.valueOf(resultSet.getString(STATUS_ROW_NAME)),
                        resultSet.getDate(CREATED_AT_ROW_NAME),
                        resultSet.getDate(IN_PROGRESS_FROM_ROW_NAME),
                        resultSet.getDate(FINISHED_AT_ROW_NAME)
                ));
            }

            return foundedOrder;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
