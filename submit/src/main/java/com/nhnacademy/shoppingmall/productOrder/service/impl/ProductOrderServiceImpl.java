package com.nhnacademy.shoppingmall.productOrder.service.impl;

import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;
import com.nhnacademy.shoppingmall.productOrder.repository.ProductOrderRepository;
import com.nhnacademy.shoppingmall.productOrder.service.ProductOrderService;

import java.util.List;
import java.util.Objects;

public class ProductOrderServiceImpl implements ProductOrderService {

    private ProductOrderRepository productOrderRepository;
    public ProductOrderServiceImpl(ProductOrderRepository productOrderRepository) {
        this.productOrderRepository = productOrderRepository;
    }
    
    @Override
    public ProductOrder getProductOrder(int productId, String orderId) {
        if(productId < 1 || Objects.isNull(orderId)) {
            throw new IllegalArgumentException("Invalid product id or order id");
        }

        return productOrderRepository.findByProductIdAndOrderId(productId, orderId).get();
    }

    @Override
    public List<ProductOrder> getProductOrderList(String orderId) {
        if(Objects.isNull(orderId)) {
            throw new IllegalArgumentException("Invalid order id");
        }

        return productOrderRepository.findByOrderId(orderId);
    }

    @Override
    public void saveProductOrder(ProductOrder productOrder) {
       if(Objects.isNull(productOrder)) {
           throw new IllegalArgumentException("Invalid product order");
       }

       productOrderRepository.save(productOrder);
    }

    @Override
    public void saveProductOrders(List<ProductOrder> productOrders) {
        if(Objects.isNull(productOrders)) {
            throw new IllegalArgumentException("Invalid product orders");
        }

        productOrders.forEach(productOrder -> {
            saveProductOrder(productOrder);
        });
    }

    @Override
    public void deleteByOrderId(String orderId) {
        if(productOrderRepository.countByOrderId(orderId) < 0) {
            throw new IllegalArgumentException("Invalid order id");
        }

        productOrderRepository.deleteByOrderId(orderId);
    }

    @Override
    public void deleteByProductIdAndOrderId(int productId, String orderId) {
        if(productOrderRepository.countByOrderId(orderId) < 0) {
            throw new IllegalArgumentException("Invalid product id or order id");
        }

        productOrderRepository.deleteByProductIdAndOrderId(productId, orderId);
    }
}
