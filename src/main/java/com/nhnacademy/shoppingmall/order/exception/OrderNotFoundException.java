package com.nhnacademy.shoppingmall.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String orderId){
        super(String.format("order not found:"+orderId));
    }
}
