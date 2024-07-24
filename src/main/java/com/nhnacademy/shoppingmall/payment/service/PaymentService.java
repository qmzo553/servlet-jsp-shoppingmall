package com.nhnacademy.shoppingmall.payment.service;

import com.nhnacademy.shoppingmall.payment.domain.Payment;

public interface PaymentService {

    Payment getPayment(String paymentId);
    void savePayment(Payment payment);
    void deletePayment(String paymentId);
}
