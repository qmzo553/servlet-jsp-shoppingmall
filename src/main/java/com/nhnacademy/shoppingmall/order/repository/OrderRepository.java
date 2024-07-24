package com.nhnacademy.shoppingmall.order.repository;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.time.LocalDateTime;
import java.util.Optional;

public interface OrderRepository {

    Optional<Order> findByOrderId(String orderId);
    int save(Order order);
    int deleteByOrderId(String orderId);
    int updateOrderStatusByOrderId(String orderId, Order.Status status);
    int updateDeliveredAtByOrderId(String orderId, LocalDateTime deliveredAt);
    int countByOrderId(String orderId);

    long totalCount(String userId);

    Page<Order> findAll(String userId, int page, int pageSize);
}
