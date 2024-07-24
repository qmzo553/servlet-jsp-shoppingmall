package com.nhnacademy.shoppingmall.payment.service.impl;

import com.nhnacademy.shoppingmall.payment.domain.Payment;
import com.nhnacademy.shoppingmall.payment.exception.PaymentAlreadyExistsException;
import com.nhnacademy.shoppingmall.payment.exception.PaymentNotFoundException;
import com.nhnacademy.shoppingmall.payment.repository.PaymentRepository;
import com.nhnacademy.shoppingmall.payment.service.PaymentService;

import java.util.Optional;

public class PaymentServiceImpl implements PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public Payment getPayment(String paymentId) {
        Optional<Payment> paymentOptional = paymentRepository.findByPaymentId(paymentId);
        return paymentOptional.orElse(null);
    }

    @Override
    public void savePayment(Payment payment) {
        if(paymentRepository.countByPaymentId(payment.getPaymentId()) > 0) {
            throw new PaymentAlreadyExistsException("Payment already exists");
        }

        paymentRepository.save(payment);
    }

    @Override
    public void deletePayment(String paymentId) {
        if(paymentRepository.countByPaymentId(paymentId) <= 0) {
            throw new PaymentNotFoundException("Payment not found");
        }

        paymentRepository.deleteByPaymentId(paymentId);
    }
}
