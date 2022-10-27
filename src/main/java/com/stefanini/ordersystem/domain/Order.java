package com.stefanini.ordersystem.domain;

import com.stefanini.ordersystem.domain.enums.OrderStatus;
import com.stefanini.ordersystem.domain.enums.OrderType;

import java.sql.Date;
import java.time.LocalDate;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.NEW;

public class Order {

    private Long id;
    private OrderType type;
    private OrderStatus status;
    private Date createdAt;
    private Date inProgressFrom;
    private Date finishedAt;


    public Order() {
    }

    public Order(Long id, OrderType type, OrderStatus status, Date createdAt, Date inProgressFrom, Date finishedAt) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.inProgressFrom = inProgressFrom;
        this.finishedAt = finishedAt;
    }

    private Order(OrderType type, OrderStatus status, Date createdAt) {
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Order createWithCreatedTimeAndEntryStatus(OrderType orderType) {
        return new Order(orderType, NEW, Date.valueOf(LocalDate.now()));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrderType getType() {
        return type;
    }

    public void setType(OrderType type) {
        this.type = type;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getInProgressFrom() {
        return inProgressFrom;
    }

    public void setInProgressFrom(Date inProgressFrom) {
        this.inProgressFrom = inProgressFrom;
    }

    public Date getFinishedAt() {
        return finishedAt;
    }

    public void setFinishedAt(Date finishedAt) {
        this.finishedAt = finishedAt;
    }

    @Override
    public String toString() {
        return "Order {" +
                "id = " + id +
                ", \ntype = " + type +
                ", \nstatus = " + status +
                ", \ncreatedAt = " + createdAt +
                ", \ninProgressFrom = " + inProgressFrom +
                ", \nfinishedAt = " + finishedAt +
                "\n}";
    }
}
