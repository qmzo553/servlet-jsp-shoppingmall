package com.nhnacademy.shoppingmall.order.exception;

public class OrderAlreadyExistsException extends RuntimeException {
    public OrderAlreadyExistsException(String orderId){
        super(String.format("order already exist : " + orderId));
    }
}
