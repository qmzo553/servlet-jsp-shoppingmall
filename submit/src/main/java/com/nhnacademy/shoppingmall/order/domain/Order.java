package com.nhnacademy.shoppingmall.order.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class Order {
    public enum Status {
        PENDING, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    }

    private String orderId;
    private String receiverName;
    private String receiverAddress;
    private Status orderStatus;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private String userId;

    public Order(String orderId, String receiverName, String receiverAddress, Status orderStatus, LocalDateTime createdAt, LocalDateTime deliveredAt, String userId) {
        this.orderId = orderId;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
        this.deliveredAt = deliveredAt;
        this.userId = userId;
    }

    public Order(String orderId, String receiverName, String receiverAddress, String userId) {
        this.orderId = orderId;
        this.receiverName = receiverName;
        this.receiverAddress = receiverAddress;
        this.orderStatus = Status.PENDING;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    public static Order createOrder(String orderId, String receiverName, String receiverAddress, String userId) {
        return new Order(orderId, receiverName, receiverAddress, Status.PENDING, LocalDateTime.now(), null, userId);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(orderId, order.orderId) &&
                Objects.equals(receiverName, order.receiverName) &&
                Objects.equals(receiverAddress, order.receiverAddress) &&
                orderStatus == order.orderStatus &&
                Objects.equals(userId, order.userId);
    }

    @Override
    public int hashCode() {return Objects.hash(orderId, receiverName, receiverAddress, orderStatus, userId);}

    @Override
    public String toString() {
        return "Order {" +
                "orderId=" + orderId + '\'' +
                ", receiverName=" + receiverName + '\'' +
                ", receiverAddress=" + receiverAddress + '\'' +
                ", orderStatus=" + orderStatus + '\'' +
                ", createdAt=" + createdAt + '\'' +
                ", deliveredAt=" + deliveredAt + '\'' +
                ", userId=" + userId + '}';
    }


}
