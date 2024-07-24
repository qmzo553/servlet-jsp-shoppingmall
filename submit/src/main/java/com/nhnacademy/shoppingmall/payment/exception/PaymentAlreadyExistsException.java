package com.nhnacademy.shoppingmall.payment.exception;

public class PaymentAlreadyExistsException extends RuntimeException {
    public PaymentAlreadyExistsException(String paymentId) {super(String.format("payment already exist:%s",paymentId));}
}
