package com.nhnacademy.shoppingmall.point.usedPoint.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class UsedPoint {

    private String usedPointId;
    private int usedPointAmount;
    private LocalDateTime usedPointAt;
    private String paymentId;
    private String userId;

    public UsedPoint(String usedPointId, int usedPointAmount, LocalDateTime usedPointAt, String paymentId, String userId) {
        this.usedPointId = usedPointId;
        this.usedPointAmount = usedPointAmount;
        this.usedPointAt = usedPointAt;
        this.paymentId = paymentId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsedPoint usedPoint = (UsedPoint) o;
        return usedPointAmount == usedPoint.usedPointAmount &&
                Objects.equals(usedPointId, usedPoint.usedPointId) &&
                Objects.equals(paymentId, usedPoint.paymentId) &&
                Objects.equals(userId, usedPoint.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usedPointId, usedPointAmount, paymentId, userId);
    }

    @Override
    public String toString() {
        return "UsedPoint {" +
                "usedPointId='" + usedPointId + '\'' +
                ", usedPointAmount='" + usedPointAmount + '\'' +
                ", usedPointAt='" + usedPointAt + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", userId='" + userId + '}';
    }
}
