package com.nhnacademy.shoppingmall.order.service;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.product.domain.Product;

public interface OrderService {

    Order getOrder(String orderId);
    void saveOrder(Order order);
    void deleteOrder(String orderId);
    void updateOrderStatus(Order order);
    void updateOrderDeliveredAt(Order order);

    long getOrderTotalCount(String userId);

    Page<Order> getOrders(String userId, int page, int pageSize);
}
