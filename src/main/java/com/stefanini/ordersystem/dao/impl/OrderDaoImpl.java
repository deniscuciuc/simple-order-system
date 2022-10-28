package com.stefanini.ordersystem.dao.impl;

import com.stefanini.ordersystem.dao.OrderDao;
import com.stefanini.ordersystem.domain.Order;
import com.stefanini.ordersystem.domain.enums.OrderStatus;
import com.stefanini.ordersystem.domain.enums.OrderType;
import com.stefanini.ordersystem.jdbc.JdbcFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.FINISHED;
import static com.stefanini.ordersystem.domain.enums.OrderStatus.IN_PROGRESS;

@Repository
public class OrderDaoImpl implements OrderDao {

    private final Logger logger = LoggerFactory.getLogger(OrderDaoImpl.class);
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
            logger.info("Order was saved in database");

            ResultSet resultSet = statement.executeQuery(getLastAddedOrderQuery);

            return getSingleOrderResultFromResultSetOrReturnNull(resultSet);
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

            List<Order> foundedOrders = new ArrayList<>();

            while (resultSet.next()) {
                foundedOrders.add(new Order(
                        resultSet.getLong(ID_ROW_NAME),
                        OrderType.valueOf(resultSet.getString(TYPE_ROW_NAME)),
                        OrderStatus.valueOf(resultSet.getString(STATUS_ROW_NAME)),
                        resultSet.getDate(CREATED_AT_ROW_NAME),
                        resultSet.getDate(IN_PROGRESS_FROM_ROW_NAME),
                        resultSet.getDate(FINISHED_AT_ROW_NAME)
                ));
            }

            if (foundedOrders.isEmpty()) logger.info("Not a single order has been found");
            else logger.info("Was found " + foundedOrders.size() + " order(s)");

            return foundedOrders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Long deleteOrderById(Long id) {
        String query = String.format("DELETE FROM %s WHERE %s = %d", ORDER_TABLE_NAME, ID_ROW_NAME, id);

        try (Connection connection = JdbcFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.execute();

            logger.info("Order with id " + id + " deleted");

            return id;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order findOrderById(Long id) {
        String query = String.format("SELECT * FROM %s WHERE %s = %d", ORDER_TABLE_NAME, ID_ROW_NAME, id);

        try (Connection connection = JdbcFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            return getSingleOrderResultFromResultSetOrReturnNull(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Order updateOrder(Order order) {
        String inProgressFrom = (order.getStatus().equals(IN_PROGRESS)) ? String.format("'%s'", order.getInProgressFrom()) : null;
        String finishedAt = (order.getStatus().equals(FINISHED)) ? String.format("'%s'", order.getFinishedAt()) : null;

        String query = String.format("UPDATE %s " +
                        "SET \n" +
                        "%s = '%s',\n" +
                        "%s = %s,\n" +
                        "%s = %s\n" +
                        "WHERE %s = %d", ORDER_TABLE_NAME, STATUS_ROW_NAME, order.getStatus(), IN_PROGRESS_FROM_ROW_NAME,
                inProgressFrom, FINISHED_AT_ROW_NAME, finishedAt, ID_ROW_NAME, order.getId());

        try (Connection connection = JdbcFactory.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.executeUpdate();

            logger.info("Order was updated");

            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Order getSingleOrderResultFromResultSetOrReturnNull(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            Order order = new Order();
            order.setId(resultSet.getLong(ID_ROW_NAME));
            order.setType(OrderType.valueOf(resultSet.getString(TYPE_ROW_NAME)));
            order.setStatus(OrderStatus.valueOf(resultSet.getString(STATUS_ROW_NAME)));
            order.setCreatedAt(resultSet.getDate(CREATED_AT_ROW_NAME));
            order.setInProgressFrom(resultSet.getDate(IN_PROGRESS_FROM_ROW_NAME));
            order.setFinishedAt(resultSet.getDate(FINISHED_AT_ROW_NAME));
            return order;
        }
        return null;
    }
}
