package com.nhnacademy.shoppingmall.point.usedPoint.exception;

public class UsedPointAlreadExistsException extends RuntimeException {
    public UsedPointAlreadExistsException(String usedPointId) {super(String.format("Used point with id '%s' already exists", usedPointId));}
}
