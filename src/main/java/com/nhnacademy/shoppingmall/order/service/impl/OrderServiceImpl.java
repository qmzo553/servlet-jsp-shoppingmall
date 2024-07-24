package com.nhnacademy.shoppingmall.order.service.impl;

import com.nhnacademy.shoppingmall.common.page.Page;
import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.exception.OrderAlreadyExistsException;
import com.nhnacademy.shoppingmall.order.exception.OrderNotFoundException;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.product.domain.Product;

import java.time.LocalDateTime;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(String orderId) {

        if(orderId == null) {
            throw new RuntimeException();
        }

        Optional<Order> orderOptional = orderRepository.findByOrderId(orderId);
        return orderOptional.orElse(null);
    }

    @Override
    public void saveOrder(Order order) {

        if(orderRepository.countByOrderId(order.getOrderId()) > 0) {
            throw new OrderAlreadyExistsException("Order already exists");
        }

        orderRepository.save(order);
    }

    @Override
    public void deleteOrder(String orderId) {

        if(orderRepository.countByOrderId(orderId) <= 0) {
            throw new OrderNotFoundException("Order not found");
        }

        orderRepository.deleteByOrderId(orderId);
    }

    @Override
    public void updateOrderStatus(Order order) {

        if(orderRepository.countByOrderId(order.getOrderId()) <= 0) {
            throw new OrderNotFoundException("Order not found");
        }

        orderRepository.updateOrderStatusByOrderId(order.getOrderId(), order.getOrderStatus());
    }

    @Override
    public void updateOrderDeliveredAt(Order order) {

        if(orderRepository.countByOrderId(order.getOrderId()) <= 0) {
            throw new OrderNotFoundException("Order not found");
        }

        orderRepository.updateDeliveredAtByOrderId(order.getOrderId(), LocalDateTime.now());
    }

    @Override
    public long getOrderTotalCount(String userId) {
        return orderRepository.totalCount(userId);
    }

    @Override
    public Page<Order> getOrders(String userId, int page, int pageSize) {
        return orderRepository.findAll(userId, page, pageSize);
    }
}
