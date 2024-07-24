package com.nhnacademy.shoppingmall.productOrder.repository;

import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;

import java.util.List;
import java.util.Optional;

public interface ProductOrderRepository {

    Optional<ProductOrder> findByProductIdAndOrderId(int productId, String orderId);
    List<ProductOrder> findByOrderId(String orderId);
    int save(ProductOrder productOrder);
    int deleteByOrderId(String orderId);
    int deleteByProductIdAndOrderId(int productId, String orderId);
    int countByOrderId(String orderId);
}
