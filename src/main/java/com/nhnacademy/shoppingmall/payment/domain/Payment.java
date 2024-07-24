package com.nhnacademy.shoppingmall.payment.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Setter
@Getter
public class Payment {

    private String paymentId;
    private int paymentTotal;
    private int paymentDiscount;
    private int paymentDelivery;
    private int paymentFinal;
    private LocalDateTime paymentAt;
    private String orderId;

    public Payment (String paymentId, int paymentTotal, int paymentDiscount, int paymentDelivery, int paymentFinal, LocalDateTime paymentAt, String orderId) {
        this.paymentId = paymentId;
        this.paymentTotal = paymentTotal;
        this.paymentDiscount = paymentDiscount;
        this.paymentDelivery = paymentDelivery;
        this.paymentFinal = paymentFinal;
        this.paymentAt = paymentAt;
        this.orderId = orderId;
    }

    public Payment(String paymentId, int paymentTotal, int paymentDiscount, int paymentDelivery, int paymentFinal, String orderId) {
        this.paymentId = paymentId;
        this.paymentTotal = paymentTotal;
        this.paymentDiscount = paymentDiscount;
        this.paymentDelivery = paymentDelivery;
        this.paymentFinal = paymentFinal;
        this.paymentAt = LocalDateTime.now();
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentTotal == payment.paymentTotal &&
                paymentDiscount == payment.paymentDiscount &&
                paymentDelivery == payment.paymentDelivery &&
                paymentFinal == payment.paymentFinal &&
                Objects.equals(paymentId, payment.paymentId) &&
                Objects.equals(orderId, payment.orderId);
    }

    @Override
    public int hashCode() {return Objects.hash(paymentId, paymentTotal, paymentDiscount, paymentDelivery, paymentFinal, paymentAt);}

    @Override
    public String toString() {
        return "Payment {" +
                "paymentId=' " + paymentId + '\'' +
                ", paymentTotal=' " + paymentTotal + '\'' +
                ", paymentDiscount=' " + paymentDiscount + '\'' +
                ", paymentDelivery=' " + paymentDelivery + '\'' +
                ", paymentFinal=' " + paymentFinal + '\'' +
                ", paymentAt=' " + paymentAt + '\'' +
                ", orderId=' " + orderId +
                '}';
    }
}
