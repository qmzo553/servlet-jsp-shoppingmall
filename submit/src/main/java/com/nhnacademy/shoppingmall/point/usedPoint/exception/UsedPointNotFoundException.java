package com.nhnacademy.shoppingmall.point.usedPoint.exception;

public class UsedPointNotFoundException extends RuntimeException {
    public UsedPointNotFoundException(String usedPointId) {super(String.format("Used point with id %s not found", usedPointId));}
}
