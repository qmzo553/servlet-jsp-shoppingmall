package com.nhnacademy.shoppingmall.payment.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String paymentId) {super(String.format("Payment Not Found:%s",paymentId));}
}
