package com.stefanini.ordersystem.domain;

import com.stefanini.ordersystem.domain.enums.OrderStatus;
import com.stefanini.ordersystem.domain.enums.OrderType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.stefanini.ordersystem.domain.enums.OrderStatus.NEW;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.SEQUENCE;

@Table(name = "Order")
@Entity(name = "Order")
public class Order {

    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = SEQUENCE, generator = "order_sequence")
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(STRING)
    private OrderType type;

    @Column(name = "status", nullable = false)
    @Enumerated(STRING)
    private OrderStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "in_progress_from", nullable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime inProgressFrom;

    @Column(name = "finished_at", nullable = false, updatable = false)
    @UpdateTimestamp
    private LocalDateTime finishedAt;

    public Order() {

    }

    private Order(OrderType type, OrderStatus status, LocalDateTime createdAt) {
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
    }

    public static Order createWithCreatedTimeAndEntryStatus(OrderType orderType) {
        return new Order(orderType, NEW, LocalDateTime.now());
    }
}
