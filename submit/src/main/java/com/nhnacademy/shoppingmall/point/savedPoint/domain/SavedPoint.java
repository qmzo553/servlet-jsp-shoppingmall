package com.nhnacademy.shoppingmall.point.savedPoint.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
public class SavedPoint {

    private String savedPointId;
    private int savedPointAmount;
    private LocalDateTime savedPointAt;
    private String paymentId;
    private String userId;

    public SavedPoint(String savedPointId, int savedPointAmount, LocalDateTime savedPointAt, String paymentId, String userId) {
        this.savedPointId = savedPointId;
        this.savedPointAmount = savedPointAmount;
        this.savedPointAt = savedPointAt;
        this.paymentId = paymentId;
        this.userId = userId;
    }

    public SavedPoint(String savedPointId, int savedPointAmount, String paymentId, String userId) {
        this.savedPointId = savedPointId;
        this.savedPointAmount = savedPointAmount;
        this.savedPointAt = LocalDateTime.now();
        this.paymentId = paymentId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedPoint savedPoint = (SavedPoint) o;
        return savedPointAmount == savedPoint.savedPointAmount &&
                Objects.equals(savedPointId, savedPoint.savedPointId) &&
                Objects.equals(paymentId, savedPoint.paymentId) &&
                Objects.equals(userId, savedPoint.userId);
    }

    @Override
    public int hashCode() {return Objects.hash(savedPointId, savedPointAmount, paymentId, userId);}

    @Override
    public String toString() {
        return "SavedPoint {" +
                "savedPointId='" + savedPointId + '\'' +
                ", savedPointAmount='" + savedPointAmount + '\'' +
                ", savedPointAt='" + savedPointAt + '\'' +
                ", paymentId='" + paymentId + '\'' +
                ", userId='" + userId + '}';
    }
}
