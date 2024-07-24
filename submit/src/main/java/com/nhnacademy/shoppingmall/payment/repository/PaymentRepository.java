package com.nhnacademy.shoppingmall.payment.repository;

import com.nhnacademy.shoppingmall.payment.domain.Payment;

import java.util.Optional;

public interface PaymentRepository {

    Optional<Payment> findByPaymentId(String paymentId);
    int save(Payment payment);
    int deleteByPaymentId(String paymentId);
    int countByPaymentId(String paymentId);
}
