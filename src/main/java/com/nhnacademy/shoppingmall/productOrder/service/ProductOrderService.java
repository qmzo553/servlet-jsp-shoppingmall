package com.nhnacademy.shoppingmall.productOrder.service;

import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;

import java.util.List;

public interface ProductOrderService {

    ProductOrder getProductOrder(int productId, String orderId);
    List<ProductOrder> getProductOrderList(String orderId);
    void saveProductOrder(ProductOrder productOrder);
    void saveProductOrders(List<ProductOrder> productOrders);
    void deleteByOrderId(String orderId);
    void deleteByProductIdAndOrderId(int productId, String orderId);
}
